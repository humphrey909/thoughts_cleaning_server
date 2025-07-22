package com.async.main.domain.face.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 생성
@Builder // 빌더 패턴 자동 생성
//얼굴 진단 재료
public class FaceDiagnosisDto {
    private int sex; //성별
    private int faceWidth; //얼굴 가로
    private int faceHeight; //얼굴 세로
    private int upperFaceLength; //상안부
    private int middleFaceLength; //중안부
    private int lowerFaceLength; //하안부
}
