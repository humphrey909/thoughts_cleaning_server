package com.async.main.domain.cleaningGauge;

import com.async.main.domain.cleaningGauge.dto.CleaningGaugeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CleaningGaugeService {

    private final CleaningGaugeRepository cleaningGaugeRepository;

    @Transactional
    public Long save(CleaningGaugeDto dto) {
        return cleaningGaugeRepository.save(dto.toEntity()).getIdx();
    }
}

