package com.async.main.domain.body._enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

//어깨 넓이
@Getter
@RequiredArgsConstructor
public enum ShoulderWidthEnum {
    // Enum 상수 정의
    SHOULDER_LARGE_TYPE("SHOULDER_LARGE", "어깨 넓은 유형"),
    SHOULDER_NARROW_TYPE("SHOULDER_NARROW", "어깨 좁은 유형"),
    SHOULDER_AVERAGE_TYPE("SHOULDER_AVERAGE", "어깨 평균 유형"),
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
    public static ShoulderWidthEnum fromCode(String code) {
        return Arrays.stream(ShoulderWidthEnum.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown order status code: " + code));
    }
}
