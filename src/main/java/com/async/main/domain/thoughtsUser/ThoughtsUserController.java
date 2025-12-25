package com.async.main.domain.thoughtsUser;

import com.async.main.common.jwt.JwtTokenProvider;
import com.async.main.domain.thoughtsUser.dto.ThoughtsUserDto;
import com.async.main.domain.user.UserRepository;
import com.async.main.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/thoughts")
@RequiredArgsConstructor
public class ThoughtsUserController {

    private final ThoughtsUserService thoughtsUserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestHeader("Authorization") String token,
            @RequestBody ThoughtsUserDto dto) {
        
        // Bearer 제거
        String actualToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(actualToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        // 토큰에서 사용자 정보 추출 (토큰에는 user.idx가 들어있음)
        Authentication auth = jwtTokenProvider.getAuthentication(actualToken);
        Long userIdx = Long.valueOf(auth.getName());

        log.info("userIdx from token: {}", userIdx);


        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        dto.setUid(user.getIdx());

        return ResponseEntity.ok(thoughtsUserService.save(dto));
    }
}
