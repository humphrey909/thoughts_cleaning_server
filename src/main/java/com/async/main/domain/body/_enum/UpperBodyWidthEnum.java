package com.async.main.domain.body._enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

//상체 길이
@Getter
@RequiredArgsConstructor
public enum UpperBodyWidthEnum {
    // Enum 상수 정의
    UPPER_LONG_TYPE("UPPER_LONG", "상체 긴 유형"),
    UPPER_SHORT_TYPE("UPPER_SHORT", "상체 짧은 유형"),
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
    public static UpperBodyWidthEnum fromCode(String code) {
        return Arrays.stream(UpperBodyWidthEnum.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown order status code: " + code));
    }
}
