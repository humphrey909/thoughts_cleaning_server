package com.async.main.domain.user;

import com.async.main.base.RO;
import com.async.main.base.ROAny;
import com.async.main.base.config.factory.ErrorMessageType;
import com.async.main.domain.user.dto.LoginRequestDto;
import com.async.main.domain.user.dto.SocialKakaoLoginRequestDto;
import com.async.main.domain.user.dto.TokenDto;
import com.async.main.domain.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.async.main.domain.user.entity.User;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 카카오 소셜 로그인
     */
    @PostMapping("/social/kakao")
    public RO socialKakaoLogin(@RequestBody SocialKakaoLoginRequestDto requestDto) {
        TokenDto tokenDto = userService.socialKakaoLogin(requestDto);

        User user = userService.getUserInfo(tokenDto.getAccessToken());
        
        ROAny json = new ROAny();
        json.put("idx", user.getIdx());
        json.put("access_token", tokenDto.getAccessToken());
        json.put("refresh_token", tokenDto.getRefreshToken());
        json.put("access_token_expires_in", tokenDto.getAccessTokenExpiresIn());

        return json;
        // return ResponseEntity.ok(tokenDto);
    }

    /**
     * login
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        TokenDto tokenDto = userService.login(loginRequestDto);

        log.info("TokenDto: {}", tokenDto);
        
        return ResponseEntity.ok(tokenDto);
    }

    /**
     * 일반 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {

        userDto.setLoginType("Y");

        userService.signup(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdraw(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        userService.withdraw(user.getUsername());
        return ResponseEntity.ok("User withdrawn successfully");
    }

    /**
     * 토큰 유효성 검사 (만료 여부 체크) 및 유저 정보 반환
     */
    @PostMapping("/check-token")
    public RO checkToken(
        @RequestHeader(value = "Authorization", required = false) String token
    ) {

        log.info("TokenDto: {}", token);

    
        // Bearer 접두사 제거
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        boolean isValid = userService.validateToken(token);
        ROAny json = new ROAny();

        if (isValid) {
            // 토큰이 유효하면 유저 정보 조회하여 반환
            User user = userService.getUserInfo(token);
            json.put("idx", user.getIdx());
            json.put("is_valid", isValid);
        } else {
            json.put("idx", 0);
            json.put("is_valid", isValid);
            // return new RO(ErrorMessageType.EX_TOKEN_EXPIRED); // 만료 또는 유효하지 않음
        }

        return json;
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public RO logout(@RequestHeader("Authorization") String token) {
        // Bearer 접두사 제거
        String accessToken = token.substring(7);
        userService.logout(accessToken);

        
        // ROAny json = new ROAny();

        // json.put("idx", user.getIdx());
        // // json.put("access_token", tokenDto.getAccessToken());
        // // json.put("refresh_token", tokenDto.getRefreshToken());
        // // json.put("access_token_expires_in", tokenDto.getAccessTokenExpiresIn());


        // return json;

        return new RO("", ErrorMessageType.OK);
    }

    /**
     * 토큰 재발급
     */
    @PostMapping("/refresh")
    public RO refresh(
        @RequestHeader(value = "Authorization", required = false) String token,
        @RequestBody Map<String, String> requestBody
    ) {
        String refreshToken = requestBody.get("refreshToken");

        // Bearer 접두사 제거 (필요한 경우)
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }
        
        TokenDto tokenDto = userService.refresh(refreshToken);
        
        // Authorization 헤더의 AccessToken (만료되었더라도 유저 식별용으로 사용 가능 여부에 따라 처리)
        // 현재 로직에서는 새로 발급된 토큰 정보를 기반으로 응답
        // 만약 기존 AccessToken으로 유저 정보를 조회하려면 validateToken 없이 파싱만 하는 로직 필요할 수 있음
        // 여기서는 새로 발급된 토큰으로 유저 정보를 조회하거나, refresh 로직 내부에서 유저 객체를 리턴받아 사용 가능
        
        // userService.refresh 내부에서 유저를 조회하므로, TokenDto 반환 전에 유저 ID 등을 함께 반환받거나
        // 여기서 다시 유저를 조회해야 함. 
        // 편의상 userService.refresh가 TokenDto만 반환하므로, TokenDto의 AccessToken을 이용해 유저 조회
        
        User user = userService.getUserInfo(tokenDto.getAccessToken());

        ROAny json = new ROAny();

        json.put("idx", user.getIdx());
        json.put("access_token", tokenDto.getAccessToken());
        json.put("refresh_token", tokenDto.getRefreshToken());
        json.put("access_token_expires_in", tokenDto.getAccessTokenExpiresIn());


        return json;
    }
}


