package com.async.main.domain.body._enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

//다리기 길이 여부
@Getter
@RequiredArgsConstructor
public enum LongPantsEnum {
    // Enum 상수 정의
    LONG_TYPE("LONG", "다리가 긴 유형"),
    SHORT_TYPE("SHORT", "다리가 짧은 유형"),
    AVERAGE_TYPE("AVERAGE", "평균 유형"),
    DEFAULT("default", "기본");

    // 필드 (각 상수가 가질 속성)
    private final String code;
    private final String description;

    /**
     * 코드 값(String)으로 해당하는 Enum 상수를 찾는 정적 메소드.
     *
     * @param code 찾으려는 상태의 코드 (예: "PAID")
     * @return 해당하는 OrderStatus 상수. 없으면 IllegalArgumentException 발생.
     */
    public static LongPantsEnum fromCode(String code) {
        return Arrays.stream(LongPantsEnum.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown order status code: " + code));
    }
}
