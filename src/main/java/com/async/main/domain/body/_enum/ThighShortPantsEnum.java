package com.async.main.domain.body._enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

//허벅지 굵기
@Getter
@RequiredArgsConstructor
public enum ThighShortPantsEnum {
    // Enum 상수 정의
    THIGH_THICK_TYPE("THIGH_THICK", "허벅지 두꺼운 유형"),
    THIGH_THIN_TYPE("THIGH_THIN", "허벅지 얇은 유형"),
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
    public static ThighShortPantsEnum fromCode(String code) {
        return Arrays.stream(ThighShortPantsEnum.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown order status code: " + code));
    }
}
