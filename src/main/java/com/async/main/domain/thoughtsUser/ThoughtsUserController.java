package com.async.main.domain.thoughtsUser;

import com.async.main.base.ROAny;
import com.async.main.common.jwt.JwtTokenProvider;
import com.async.main.domain.kindThought.entity.KindThought;
import com.async.main.domain.kindThought.repository.KindThoughtRepository;
import com.async.main.domain.thoughtsUser.dto.ThoughtsUserDto;
import com.async.main.domain.thoughtsUser.entity.ThoughtsUser;
import com.async.main.domain.user.UserRepository;
import com.async.main.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/thoughts")
@RequiredArgsConstructor
public class ThoughtsUserController {

    private final ThoughtsUserService thoughtsUserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final KindThoughtRepository kindThoughtRepository;

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

        dto.setUid(user.getIdx().intValue());

        ThoughtsUser savedUser = thoughtsUserService.save(dto);
        ROAny response = new ROAny();
        response.put("idx", savedUser.getIdx());
        response.put("content_thought", savedUser.getContentThought());
        
        // if (savedUser.getKindThoughtIdx() != null) {
        //     KindThought kindThought = kindThoughtRepository.findById(Long.valueOf(savedUser.getKindThoughtIdx()))
        //             .orElse(null);
        //     if (kindThought != null) {
        //         response.put("name", kindThought.getName());
        //         response.put("detail_text", kindThought.getDetailText());
        //     }
        // }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(actualToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        Authentication auth = jwtTokenProvider.getAuthentication(actualToken);
        Long userIdx = Long.valueOf(auth.getName());

        List<ThoughtsUser> list = thoughtsUserService.findAllByUid(userIdx.intValue());
        
        ROAny response = new ROAny();
        response.put("thoughts_list", list);

        return ResponseEntity.ok(response);
    }
}
