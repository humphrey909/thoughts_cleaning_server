package com.async.main.domain.kindThought.dto;

import com.async.main.domain.kindThought.entity.KindThought;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KindThoughtDto {
    private Long idx;
    private String name;
    private String detailText;

    public static KindThoughtDto from(KindThought entity) {
        return KindThoughtDto.builder()
                .idx(entity.getIdx())
                .name(entity.getName())
                .detailText(entity.getDetailText())
                .build();
    }
}

