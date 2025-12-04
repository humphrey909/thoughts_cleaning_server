package com.async.main.domain.thoughtsUser.dto;

import com.async.main.domain.thoughtsUser.entity.ThoughtsUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThoughtsUserDto {
    private Long idx;
    private Long uid;
    private String kindThought;
    private String contentThought;

    public ThoughtsUser toEntity() {
        return ThoughtsUser.builder()
                .uid(uid)
                .kindThought(kindThought)
                .contentThought(contentThought)
                .build();
    }
}

