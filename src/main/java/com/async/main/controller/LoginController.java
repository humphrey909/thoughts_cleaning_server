package com.async.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class LoginController {

    /**
     * 2. 특정 ID로 사용자 한 명 조회
     * GET /api/users/{id}
     */
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
//        // UserResponseDto user = userService.findById(id);
//        // return ResponseEntity.ok(user);
//
//        // --- 예제용 더미 데이터 ---
//        // 실제로는 Optional 등으로 받아 404 처리 로직이 필요합니다.
//        System.out.println("Requested User ID: " + id);
//        UserResponseDto dummyUser = new UserResponseDto("user" + id, "user" + id + "@example.com");
//        return ResponseEntity.ok(dummyUser);
//    }
}
