package com.async.main.domain.body._enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

//종아리 굵기
@Getter
@RequiredArgsConstructor
public enum CalfShortPantsEnum {
    // Enum 상수 정의
    CALF_THICK_TYPE("CALF_THICK", "종아리 두꺼운 유형"),
    CALF_THIN_TYPE("CALF_THIN", "종아리 얇은 유형"),
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
    public static CalfShortPantsEnum fromCode(String code) {
        return Arrays.stream(CalfShortPantsEnum.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown order status code: " + code));
    }
}
