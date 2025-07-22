package com.async.main.domain.body.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 생성
@Builder // 빌더 패턴 자동 생성
//체형 진단 재료
public class BodyDiagnosisDto {

    private int sex; //성별
    private int calf_width; //종아리 길이
    private int calf_circumference; //종아리 둘레
    private int thigh_circumference; //허벅지 둘레
    private int outseam; //아웃심
    private int inseam; //인심
    private int height; //키
    private int armCircumference; //팔 둘레
    private int shoulder; //어깨 단면
    private int buttock_circumference; //엉덩이 둘레
    private int waist_circumference; //허리 둘레
}
