package com.async.main.domain.thoughtsUser.dto;

import com.async.main.domain.thoughtsUser.entity.ThoughtsUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThoughtsUserDto {

    @JsonProperty("idx")
    private Integer idx;

    @JsonProperty("uid")
    private Integer uid;

    @JsonProperty("kind_thought_idx")
    private Integer kindThoughtIdx;

    @JsonProperty("content_thought")
    private String contentThought;

    public ThoughtsUser toEntity() {
        return ThoughtsUser.builder()
                .uid(uid)
                .kindThoughtIdx(kindThoughtIdx)
                .contentThought(contentThought)
                .build();
    }
}

