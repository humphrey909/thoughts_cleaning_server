package com.async.main.domain.cleaningGauge.dto;

import com.async.main.domain.cleaningGauge.entity.CleaningGauge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CleaningGaugeDto {
    
    @JsonProperty("idx")
    private Integer idx;

    @JsonProperty("thought_idx")
    private Long thoughtIdx;

    @JsonProperty("space_type")
    private String spaceType;

    @JsonProperty("components_type")
    private String componentsType;

    @JsonProperty("clean_gauge")
    private Float cleanGauge;

    public CleaningGauge toEntity() {
        return CleaningGauge.builder()
                .thoughtIdx(thoughtIdx)
                .spaceType(spaceType)
                .componentsType(componentsType)
                .cleanGauge(cleanGauge)
                .build();
    }
}

