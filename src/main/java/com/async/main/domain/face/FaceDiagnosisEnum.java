package com.async.main.domain.face;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FaceDiagnosisEnum {
    // Enum 상수 정의
    EGG_TYPE("EGG", "계란형"),
    LONG_TYPE("LONG", "긴형"),
    EGG_LONG_TYPE("EGG_LONG", "계란긴_사이"),
    ROUND_TYPE("ROUND", "둥근형"),
    INVERT_TRIANGLE_TYPE("INVERT_TRIANGLE", "역삼각형"),
    SQUARE_TYPE("SQUARE", "사각형"),
    PEANUT_TYPE("PEANUT", "땅콩형"),
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
    public static FaceDiagnosisEnum fromCode(String code) {
        return Arrays.stream(FaceDiagnosisEnum.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown order status code: " + code));
    }
}
