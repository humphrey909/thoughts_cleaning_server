package com.async.main.domain.body;

import com.async.main.domain.body.dto.BodyDiagnosisDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class BodyDiagnosisController {
    private final BodyDiagnosisService bodyDiagnosisService;

    public BodyDiagnosisController(BodyDiagnosisService bodyDiagnosisService) {
        this.bodyDiagnosisService = bodyDiagnosisService;
    }

    /**
     * 얼굴
     */
    @PostMapping
    public ResponseEntity<String> makeBodyDiagnosis(@RequestBody BodyDiagnosisDto bodyDiagnosisDto) {
        // 클라이언트로부터 받은 데이터 확인
//        System.out.println("Received Username: " + userDto.username());
//        System.out.println("Received Email: " + userDto.email());

        // 여기에 실제 비즈니스 로직(DB 저장 등)을 추가합니다.
//        bodyDiagnosisService.createUser(bodyDiagnosisDto);

        // 성공적으로 처리되었음을 알리는 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
    }
}


