package com.async.main.domain.user;

import com.async.main.common.jwt.JwtTokenProvider;
import com.async.main.domain.token.service.TokenService;
import com.async.main.domain.user.dto.LoginRequestDto;
import com.async.main.domain.user.dto.SocialKakaoLoginRequestDto; // 추가
import com.async.main.domain.user.dto.TokenDto;
import com.async.main.domain.user.dto.UserDto;
import com.async.main.domain.user.entity.SocialUser;
import com.async.main.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 로그 사용을 위해 추가 (없다면 클래스에 @Slf4j 붙여야 함)
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j // 로그 애노테이션 추가
@Service
@Transactional(readOnly = true) // 클래스 전체에 읽기 전용 트랜잭션 적용
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    /**
     * 카카오 소셜 로그인 (데이터 확인용)
     */
    @Transactional
    public TokenDto socialKakaoLogin(SocialKakaoLoginRequestDto requestDto) {
        log.info("========== Kakao Social Login Request ==========");
        log.info("Kakao User ID: {}", requestDto.getKakaoUserId());
        log.info("Access Token: {}", requestDto.getAccessToken());
        log.info("Refresh Token: {}", requestDto.getRefreshToken());
        log.info("Profile: {}", requestDto.getProfile());
        log.info("Device Info - AppId: {}, Model: {}, OS: {}", 
                requestDto.getAppId(), requestDto.getModelName(), requestDto.getPhoneOSVersion());

        // 입력 형식을 정의합니다.
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss O yyyy", Locale.ENGLISH);
        // 출력 형식을 정의합니다.
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String accessFormattedDate = "";
        String refreshFormattedDate = "";

        try {
            if (requestDto.getAccessExpires() != null) {
                ZonedDateTime accessZonedDateTime = ZonedDateTime.parse(requestDto.getAccessExpires(), inputFormatter);
                accessFormattedDate = accessZonedDateTime.format(outputFormatter);
            }

            if (requestDto.getRefreshExpires() != null) {
                ZonedDateTime refreshZonedDateTime = ZonedDateTime.parse(requestDto.getRefreshExpires(), inputFormatter);
                refreshFormattedDate = refreshZonedDateTime.format(outputFormatter);
            }
        } catch (DateTimeParseException e) {
            log.error("Error parsing date: {}", e.getMessage());
        }

        String accessExpires = accessFormattedDate.isEmpty() ? requestDto.getAccessExpires() : accessFormattedDate;
        String refreshExpires = refreshFormattedDate.isEmpty() ? requestDto.getRefreshExpires() : refreshFormattedDate;

        
        String kakaoUserId = requestDto.getKakaoUserId();
        
        // 1. DB 조회
        Optional<User> userOptional = userRepository.findByLoginId(kakaoUserId);
        User user;

        // 프로필 정보 추출
        String name = "Unknown";
        String email = "";

        if (requestDto.getProfile() != null) {
            Map<String, Object> profile = requestDto.getProfile();
            
            // 이메일 추출 (최상위)
            if (profile.get("email") != null) {
                email = String.valueOf(profile.get("email"));
            }

            // 닉네임 추출 (profile 객체 내부)
            if (profile.containsKey("profile")) {
                Object profileObj = profile.get("profile");
                if (profileObj instanceof Map) {
                    Map<String, Object> innerProfile = (Map<String, Object>) profileObj;
                    if (innerProfile.get("nickname") != null) {
                        name = String.valueOf(innerProfile.get("nickname"));
                    }
                } else if (profileObj != null) {
                    // 혹시라도 profile이 String 형태로 올 경우 (드물지만 안전하게)
                    // name = profileObj.toString(); 
                    // 위 로그상 {nickname=...} 이므로 Map이 맞음
                }
            }
        }

        if (userOptional.isEmpty()) {
            // 2. 없으면 회원가입 처리
            user = User.builder()
                    .loginId(kakaoUserId)
                    .password(passwordEncoder.encode("KAKAO_SOCIAL_LOGIN")) // 소셜 로그인은 비밀번호가 불필요하지만 DB 제약조건 때문에 더미 값 설정
                    .loginType("KAKAO")
                    .name(name)
                    .email(email)
                    .appOsType(requestDto.getModelName() != null ? requestDto.getModelName() : "Unknown")
                    .osVersion(requestDto.getPhoneOSVersion())
                    .roles(Collections.singletonList("U"))
                    .build();
            userRepository.save(user);
        } else {
            user = userOptional.get();
            
            // 탈퇴한 사용자라면 복구 (재가입 처리)
            if (user.getDeleteTime() != null) {
                user.restore();
            }

            // 3. 있으면 정보 업데이트 (기기 정보 등)
            user.updateInfo(
                name,
                email,
                requestDto.getModelName() != null ? requestDto.getModelName() : "Unknown",
                requestDto.getPhoneOSVersion(),
                null, 
                null
            );
        }

        // 4. SocialUser 테이블 저장/업데이트
        SocialUser socialUser = socialUserRepository.findByUserIdAndLoginCompany(user.getIdx(), "KAKAO")
                .orElse(null);

        if (socialUser == null) {
            socialUser = SocialUser.builder()
                    .userId(user.getIdx())
                    .loginCompany("KAKAO")
                    .accessToken(requestDto.getAccessToken())
                    .accessTokenExpire(accessExpires)
                    .refreshToken(requestDto.getRefreshToken())
                    .refreshTokenExpire(refreshExpires)
                    .build();
            socialUserRepository.save(socialUser);
        } else {
            // 이미 존재하면 토큰 정보 업데이트
            if (socialUser.getDeleteTime() != null) {
                socialUser.restore();
            }
            socialUser.updateToken(
                    requestDto.getAccessToken(),
                    accessExpires,
                    requestDto.getRefreshToken(),
                    refreshExpires
            );
        }

        // 5. 로그인 처리 (토큰 발급)
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getIdx(), "", authorities);
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);

        // Refresh Token 저장
        tokenService.saveOrUpdateToken(user.getIdx(), tokenDto.getRefreshToken(), "Unknown");

        //로그인 처리 진행
        return tokenDto;
    }

    /**
     * 일반 회원가입
     */
    @Transactional
    public void signup(UserDto userDto) {
        if (userRepository.findByLoginId(userDto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .loginId(userDto.getLoginId())
                .password(passwordEncoder.encode(userDto.getPassword())) // 비밀번호 암호화
                .loginType(userDto.getLoginType())
                .fcmId(userDto.getFcmId())
                .appOsType(userDto.getAppOsType())
                .osVersion(userDto.getOsVersion())
                .appVersion(userDto.getAppVersion())
                .roles(Collections.singletonList("U")) // 기본 권한 설정
                .build();

        userRepository.save(user);
    }

    /**
     * 회원 탈퇴 (Soft Delete)
     */
    @Transactional
    public void withdraw(String accessToken) {
        // 1. 토큰 유효성 검증
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 토큰입니다.");
        }

        // 2. 토큰에서 유저 정보(PK) 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        Long userId = Long.valueOf(authentication.getName());

        // 3. User 조회 및 Soft Delete
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.delete();

        // 4. SocialUser 조회 및 Soft Delete
        List<SocialUser> socialUsers = socialUserRepository.findAllByUserId(userId);
        for (SocialUser socialUser : socialUsers) {
            socialUser.delete();
        }

        // 5. Refresh Token 삭제
        tokenService.deleteRefreshToken(userId);
    }

    /**
     * 로그인
     */
    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByLoginId(loginRequestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        
        if (user.getDeleteTime() != null) {
            throw new IllegalArgumentException("탈퇴한 사용자입니다.");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getIdx(), "", authorities);

        // log.info("authentication: {}", loginRequestDto);
        log.info("authentication: {}", authentication);


        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);

        // Refresh Token DB 저장
        tokenService.saveOrUpdateToken(user.getIdx(), tokenDto.getRefreshToken(), "Unknown");

        return tokenDto;
    }

    /**
     * 내 정보 조회
     */
    @Transactional(readOnly = true)
    public User getUserInfo(String accessToken) {
        // Access Token 유효성 검증 (여기서도 한번 더 체크하거나 생략 가능, validateToken 호출 했으므로)
        // 하지만 getAuthentication 내부적으로 파싱을 하기 때문에 토큰이 유효해야 함.
        
        // Access Token에서 User ID 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        Long userId = Long.valueOf(authentication.getName());

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    /**
     * 토큰 유효성 검사
     */
    @Transactional(readOnly = true)
    public boolean validateToken(String accessToken) {
        return jwtTokenProvider.validateToken(accessToken);
    }
    
    /**
     * 로그아웃 (Session Key 삭제 -> Refresh Token 삭제)
     */
    @Transactional
    public void logout(String accessToken) {
        // Access Token 유효성 검증
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // Access Token에서 User ID 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        Long userId = Long.valueOf(authentication.getName());

        tokenService.deleteRefreshToken(userId);
    }

    /**
     * 토큰 재발급
     */
    @Transactional
    public TokenDto refresh(String refreshToken) {
        // 1. Refresh Token 검증 (DB에 있는 값과 일치하는지, 만료되지 않았는지)
        if (!tokenService.validateToken(refreshToken)) {
            log.error("Invalid or expired refresh token: {}", refreshToken);
            throw new IllegalArgumentException("유효하지 않거나 만료된 Refresh Token입니다.");
        }

        // 2. 토큰에서 유저 정보 추출 (TokenService나 DB 조회를 통해)
        Long uid;
        try {
            uid = tokenService.getUidFromToken(refreshToken);
        } catch (Exception e) {
            log.error("Failed to get uid from refresh token: {}", refreshToken, e);
            throw e;
        }

        User user = userRepository.findById(uid)
                .orElseThrow(() -> {
                    log.error("User not found for uid: {}", uid);
                    return new IllegalArgumentException("존재하지 않는 사용자입니다.");
                });

        // 3. 새로운 토큰 발급
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getIdx(), "", authorities);
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);

        // 4. DB에 새로운 Refresh Token 저장 (기존 토큰 업데이트)
        try {
            tokenService.saveOrUpdateToken(user.getIdx(), tokenDto.getRefreshToken(), "Unknown");
        } catch (Exception e) {
            log.error("Failed to save/update refresh token for user idx: {}", user.getIdx(), e);
            throw e;
        }

        return tokenDto;
    }
}
