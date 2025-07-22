package com.async.main.domain.body._enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum BodyDiagnosisEnum {
    // Enum 상수 정의
    SANDGLASS_TYPE("SANDGLASS", "모래시계"),
    SANDGLASS_RECTANGLE_TYPE("SANDGLASS_RECTANGLE", "모래시계_직사각형"),
    INVERT_TRIANGLE_TYPE("INVERT_TRIANGLE", "역삼각형"),
    TRIANGLE_TYPE("TRIANGLE", "삼각형"),
    RECTANGLE_TYPE("RECTANGLE", "직사각형"),
    RECTANGLE_TRIANGLE_TYPE("RECTANGLE_TRIANGLE", "직사각형_삼각형"),

    ROUND_TYPE("ROUND", "타원형"),
    STRAIGHT_TYPE("STRAIGHT", "직선형"),
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
    public static BodyDiagnosisEnum fromCode(String code) {
        return Arrays.stream(BodyDiagnosisEnum.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown order status code: " + code));
    }
}
