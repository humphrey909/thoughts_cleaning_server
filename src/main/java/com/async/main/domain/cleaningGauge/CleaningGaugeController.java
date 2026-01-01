package com.async.main.domain.cleaningGauge;

import com.async.main.common.jwt.JwtTokenProvider;
import com.async.main.domain.cleaningGauge.dto.CleaningGaugeDto;
import com.async.main.domain.thoughtsUser.ThoughtsUserRepository;
import com.async.main.domain.thoughtsUser.entity.ThoughtsUser;
import com.async.main.domain.user.UserRepository;
import com.async.main.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cleaning-gauge")
@RequiredArgsConstructor
public class CleaningGaugeController {

    private final CleaningGaugeService cleaningGaugeService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ThoughtsUserRepository thoughtsUserRepository;

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestHeader("Authorization") String token,
            @RequestBody CleaningGaugeDto dto) {

        // Bearer 제거
        String actualToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(actualToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        // 토큰에서 사용자 정보 추출
        Authentication auth = jwtTokenProvider.getAuthentication(actualToken);
        Long userIdx = Long.valueOf(auth.getName());

        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 요청한 thoughtIdx가 해당 유저의 것인지 검증
        ThoughtsUser thoughtsUser = thoughtsUserRepository.findById(dto.getThoughtIdx())
                .orElseThrow(() -> new IllegalArgumentException("Thought not found"));

        if (thoughtsUser.getUid().longValue() != user.getIdx().longValue()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to access this thought");
        }
        
        return ResponseEntity.ok(cleaningGaugeService.save(dto));
    }
}
