package com.async.main.domain.cleaningGauge.dto;

import com.async.main.domain.cleaningGauge.entity.CleaningGauge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CleaningGaugeDto {
    private Long idx;
    private Long thoughtIdx;
    private String spaceType;
    private String componentsType;
    private Double cleanGauge;

    public CleaningGauge toEntity() {
        return CleaningGauge.builder()
                .thoughtIdx(thoughtIdx)
                .spaceType(spaceType)
                .componentsType(componentsType)
                .cleanGauge(cleanGauge)
                .build();
    }
}

