package com.async.main.domain.thoughtsUser;

import com.async.main.domain.thoughtsUser.dto.ThoughtsUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thoughts")
@RequiredArgsConstructor
public class ThoughtsUserController {

    private final ThoughtsUserService thoughtsUserService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody ThoughtsUserDto dto) {
        return ResponseEntity.ok(thoughtsUserService.save(dto));
    }
}
