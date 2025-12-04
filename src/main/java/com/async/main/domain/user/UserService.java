package com.async.main.domain.user;

import com.async.main.common.jwt.JwtTokenProvider;
import com.async.main.domain.user.dto.LoginRequestDto;
import com.async.main.domain.user.dto.TokenDto;
import com.async.main.domain.user.dto.UserDto;
import com.async.main.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true) // 클래스 전체에 읽기 전용 트랜잭션 적용
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
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
                .sessionKey(userDto.getSessionKey())
                .fcmId(userDto.getFcmId())
                .appOsType(userDto.getAppOsType())
                .osVersion(userDto.getOsVersion())
                .appVersion(userDto.getAppVersion())
                .roles(Collections.singletonList("ROLE_USER")) // 기본 권한 설정
                .build();

        userRepository.save(user);
    }

    /**
     * 회원 탈퇴 (Soft Delete)
     */
    @Transactional
    public void withdraw(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        
        user.delete(); // BaseTimeEntity의 delete 메서드 호출 (delete_time 설정)
    }

    /**
     * 로그인
     */
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

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getLoginId(), "", authorities);

        return jwtTokenProvider.generateToken(authentication);
    }
}
