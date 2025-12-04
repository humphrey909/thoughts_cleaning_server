package com.async.main.domain.cleaningGauge;

import com.async.main.domain.cleaningGauge.dto.CleaningGaugeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cleaning-gauge")
@RequiredArgsConstructor
public class CleaningGaugeController {

    private final CleaningGaugeService cleaningGaugeService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody CleaningGaugeDto dto) {
        return ResponseEntity.ok(cleaningGaugeService.save(dto));
    }
}

