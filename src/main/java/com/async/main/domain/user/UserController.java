package com.async.main.domain.user;

import com.async.main.domain.user.dto.LoginRequestDto;
import com.async.main.domain.user.dto.TokenDto;
import com.async.main.domain.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        userService.signup(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdraw(@AuthenticationPrincipal User user) {
        userService.withdraw(user.getUsername());
        return ResponseEntity.ok("User withdrawn successfully");
    }
}


