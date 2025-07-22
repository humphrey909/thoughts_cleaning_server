package com.async.main.domain.body;

import com.async.main.domain.body._enum.*;
import com.async.main.domain.body.dto.BodyDiagnosisDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 클래스 전체에 읽기 전용 트랜잭션 적용
public class BodyDiagnosisService {





    /**
     * 체형 계산
     */
    public Long calculateBodyType(BodyDiagnosisDto bodyDiagnosisDto) {

    }


    /**
     * 종아리 긴바지 진단
     * - 종아리 길이 / 아웃심 길이 * 100 = val
     * - val > 44.5% ~ 45.5% = 다리가 길다. 긴바지를 짧게 입기 가능(클래식, 카프리, 앵클)
     * - val < 44.5% ~ 45.5% = 다리가 짧다. 긴바지를 길게 입어야 한다.(클래식, 슈퍼롱)
     * */
    public LongPantsEnum calculateLongPants(BodyDiagnosisDto bodyDiagnosisDto) {

        LongPantsEnum longPantsEnum;
        double val1 = (double) bodyDiagnosisDto.getCalf_width() / bodyDiagnosisDto.getOutseam() * 100;

        if (val1 < 44.5) { //다리가 짧다. 긴바지를 길게 입어야 한다.(클래식, 슈퍼롱)
            longPantsEnum = LongPantsEnum.LONG_TYPE;
        } else if (val1 > 45.5) { //다리가 길다. 긴바지를 짧게 입기 가능(클래식, 카프리, 앵클)
            longPantsEnum = LongPantsEnum.SHORT_TYPE;
        } else { //평균
            longPantsEnum = LongPantsEnum.AVERAGE_TYPE;
        }
        return longPantsEnum;
    }


    /**
     * 종아리까지 오는 숏타입 반바지, 미디(무릅 - 발목 사이 스커트 여부를 체크)
     * - 키 * 0.25 = val (+2cm)
     *     - val > 종아리 둘레 : 짧은 펜치 가능( 더 짧은 니(무릅까지) 이상 가능)
     *     - val < 종아리 둘레 : 짧은 펜치 입으면 별로일 수 있음.(종아리를 가려주는 미디까지 가능)
     * */
    public CalfShortPantsEnum calculateCalfShortPants(BodyDiagnosisDto bodyDiagnosisDto) {

        CalfShortPantsEnum calfShortPantsEnum;
        double val1 = bodyDiagnosisDto.getHeight() * 0.25;
        double val1_range = val1 + 2; //오차범위 허용

        if(val1 > bodyDiagnosisDto.getCalf_circumference()){ //더 짧은 니(무릅까지) 이상 가능
            calfShortPantsEnum = CalfShortPantsEnum.CALF_THIN_TYPE;
        }else if(val1_range < bodyDiagnosisDto.getCalf_circumference()) {
            calfShortPantsEnum = CalfShortPantsEnum.CALF_THICK_TYPE; //종아리를 가려주는 미디까지 가능
        }else{
            calfShortPantsEnum = CalfShortPantsEnum.AVERAGE_TYPE;
        }

        return calfShortPantsEnum;
    }


    /**
     * 허벅지를 가리는 반바지 길이, 바지 핏
     * - 키 * 0.295 = val (+5cm)
     *     - val < 허벅지 둘레 : 통통한 다리 (마이크로 쇼츠, 쇼츠 불가, 버뮤다 까지 가능), 넓은 핏(루즈, 와이드, 오버 핏 추천)
     *     - val > 허벅지 둘레 : 마른 다리 (마이크로 쇼츠, 쇼츠까지 가능), 얇은 핏(스탠다드, 슬림, 스키니 핏, 플레어 팬츠, 나팔바지)
     *     - val = 허벅지 둘레 : 평균 다리 (마이크로 쇼츠, 쇼츠까지 가능), 보통 핏(스탠다드, 슬림 핏)
     * */
    public ThighShortPantsEnum calculateThighShortPants(BodyDiagnosisDto bodyDiagnosisDto) {

        ThighShortPantsEnum thighShortPantsEnum;
        double val1 = bodyDiagnosisDto.getHeight() * 0.295;
        double val1_range = val1 + 5; //오차범위 허용

        if(val1 > bodyDiagnosisDto.getThigh_circumference()){ //마이크로 쇼츠, 쇼츠까지 가능
            thighShortPantsEnum = ThighShortPantsEnum.THIGH_THIN_TYPE;
        }else if(val1_range < bodyDiagnosisDto.getThigh_circumference()) {
            thighShortPantsEnum = ThighShortPantsEnum.THIGH_THICK_TYPE; //마이크로 쇼츠, 쇼츠 불가, 버뮤다 까지 가능
        }else{
            thighShortPantsEnum = ThighShortPantsEnum.AVERAGE_TYPE; //마이크로 쇼츠, 쇼츠까지 가능
        }

        return thighShortPantsEnum;
    }

    /**
     * 웨스트, 라이즈, 밑위 진단 (허리 길이 여부)
     * - 인심 /키 * 100 = val
     *     - val > 45.5 : 상체가 짧다. (로우 라이즈, 미들 라이즈 가능)
     *     - val < 45.5 : 상체가 길다. (미들 라이즈, 하이웨스트로 상체를 줄여줄 것)
     * */
    public UpperBodyWidthEnum calculateUpperBodyType(BodyDiagnosisDto bodyDiagnosisDto) {

        UpperBodyWidthEnum upperBodyWidthEnum = UpperBodyWidthEnum.DEFAULT;
        double val1 = (double) bodyDiagnosisDto.getInseam() /bodyDiagnosisDto.getHeight() * 100;
        double average_val = 45.5;

        if(val1 > average_val){ //상체가 짧다.
            upperBodyWidthEnum = UpperBodyWidthEnum.UPPER_SHORT_TYPE;
        }else if(val1 < average_val){ //상체가 길다.
            upperBodyWidthEnum = UpperBodyWidthEnum.UPPER_LONG_TYPE;
        }else{ //평균
            upperBodyWidthEnum = UpperBodyWidthEnum.AVERAGE_TYPE;
        }

        return upperBodyWidthEnum;
    }

    /**
     * 팔 진단
     * - 키 * 0.145 = val (+2cm)
     *     - val > 내 팔 둘레 : 얇음 (마리, 프렌치, 퍼프, 타이트)
     *     - val < 내 팔 둘레 : 두꺼움 (플레어, 벨)
     * */
    public ArmCircumferenceEnum calculateArmCircumference(BodyDiagnosisDto bodyDiagnosisDto) {

        ArmCircumferenceEnum armCircumferenceEnum = ArmCircumferenceEnum.DEFAULT;
        double val1 = (double) bodyDiagnosisDto.getHeight() * 0.145;
        double val1_range = val1 + 2; //오차범위 허용


        if(val1 > bodyDiagnosisDto.getThigh_circumference()){ //팔 얇음
            armCircumferenceEnum = ArmCircumferenceEnum.ARM_THIN_TYPE;
        }else if(val1_range < bodyDiagnosisDto.getThigh_circumference()) {
            armCircumferenceEnum = ArmCircumferenceEnum.ARM_THICK_TYPE; //팔 두거움
        }else{
            armCircumferenceEnum = ArmCircumferenceEnum.AVERAGE_TYPE; //팔 평균
        }

        return armCircumferenceEnum;
    }

    /**
     * 어깨 진단
     * 내 어깨 단면 > 36~38 : 기본, 타이트 (어깨가 넓은 경우)
     * 내 어깨 단면 < 36~38 : 파워 숄더, 마리, 프렌치, 퍼프 (어깨가 좀은 경우)
     * 내 어깨 단면 = 36~38 : 패드 (어깨 평균)
     * */
    public ShoulderWidthEnum calculateShoulderWidth(BodyDiagnosisDto bodyDiagnosisDto) {

        ShoulderWidthEnum shoulderWidthEnum = ShoulderWidthEnum.DEFAULT;

        int average_shoulder = 36;
        int average_shoulder_range = 38;

        if(average_shoulder > bodyDiagnosisDto.getShoulder()){ //어깨 좁은 경우
            shoulderWidthEnum = ShoulderWidthEnum.SHOULDER_NARROW_TYPE;
        }else if(average_shoulder_range < bodyDiagnosisDto.getShoulder()){ //어깨 넓은 경우
            shoulderWidthEnum = ShoulderWidthEnum.SHOULDER_LARGE_TYPE;
        }else{
            shoulderWidthEnum = ShoulderWidthEnum.SHOULDER_AVERAGE_TYPE; //어깨 평균
        }

        return shoulderWidthEnum;
    }


    /**
     * 힙 진단
     * 내 힙 둘레 > 85~87 : 힙의 아웃라인만 살려줄 것 (H라인, 랩 스커트, 머메이드, 플레어 X, 샤 스커트 X)
     * 내 힙 둘레 < 85~87 : 주름, 벌룬으로 포인트를 줄 것 (길트 스커트, 테니스 스커트, 플레어 )
     * 내 힙 둘레 = 85~87 : A라인
     * */
    public ButtockCircumferenceEnum calculateButtockCircumference(BodyDiagnosisDto bodyDiagnosisDto) {

        ButtockCircumferenceEnum buttockCircumferenceEnum = ButtockCircumferenceEnum.DEFAULT;
        int average_buttock = 85;
        int average_buttock_range = 87;

        if(average_buttock > bodyDiagnosisDto.getButtock_circumference()){ //엉덩이 좁은 경우
            buttockCircumferenceEnum = ButtockCircumferenceEnum.BUTTOCK_NARROW_TYPE;
        }else if(average_buttock_range < bodyDiagnosisDto.getButtock_circumference()){ //엉덩이 넓은 경우
            buttockCircumferenceEnum = ButtockCircumferenceEnum.BUTTOCK_LARGE_TYPE;
        }else{
            buttockCircumferenceEnum = ButtockCircumferenceEnum.BUTTOCK_AVERAGE_TYPE; //엉덩이 평균
        }

        return buttockCircumferenceEnum;
    }




    /**
     * 어깨 허리 힙 비율 조합 - 여자
     * - 어깨와 힙이 2칸 이상 차이 날 경우 : 모래시계
     *     - 힙 - 허리 = val
     *         1. val ≥ 20 : 완벽한 모래시계
     *         2. val ≥ 19~17 : 모래시계 + 직사각형
     *         3. val ≥ 16~14 : 직사각형
     *         4. val ≤ 13 : 타원형
     * - 어깨와 힙이 3칸 이상 차이 날 경우 : 모래시계가 불가한 삼각형
     *     1. 어깨 = 허리 or 어깨 < 허리 : 직사각형 + 삼각형 가능성
     *     2. 어깨 > 허리 : 완벽한 삼각형(허리가 얇을때)
     * - 어깨와 힙 차이가 0이라면 직사각형
     * - 어깨보다 힙이 더 작다면 역삼각형 - 0 보다 작은 경우
     * - 전체적으로 평균 이하 → 직선형이고 + 역삼이 올 수 도
     * */
    public BodyDiagnosisEnum calculateThreeAreaCombinationWoman(BodyDiagnosisDto bodyDiagnosisDto) {

        BodyDiagnosisEnum bodyDiagnosisEnum = BodyDiagnosisEnum.DEFAULT;

        //평균의 기준이 되는 값
        int average_shoulder_standard = 37;
        int average_waist_standard = 69;
        int average_buttock_standard = 86;

        double myShoulderDiffSpace = bodyDiagnosisDto.getShoulder() - average_shoulder_standard;
        double myWaistDiffSpace = bodyDiagnosisDto.getWaist_circumference() - average_waist_standard;
        double myButtockDiffSpace = bodyDiagnosisDto.getButtock_circumference() - average_buttock_standard;

        double diffShoulderButtockSpace = myButtockDiffSpace - myShoulderDiffSpace; //엉덩이와 어깨 차

        if(diffShoulderButtockSpace == 2){
            double diffButtockWaist = bodyDiagnosisDto.getButtock_circumference() - bodyDiagnosisDto.getWaist_circumference();
            if(diffButtockWaist >= 20){ //완벽한 모래시계
                bodyDiagnosisEnum = BodyDiagnosisEnum.SANDGLASS_TYPE;
            }else if(diffButtockWaist <= 19 && diffButtockWaist >= 17){ //모래시계 + 직사가형
                bodyDiagnosisEnum = BodyDiagnosisEnum.SANDGLASS_RECTANGLE_TYPE;
            }else if(diffButtockWaist <= 16 && diffButtockWaist >= 14){ //직사각형
                bodyDiagnosisEnum = BodyDiagnosisEnum.RECTANGLE_TYPE;
            }else if(diffButtockWaist <= 13){ //타원형
                bodyDiagnosisEnum = BodyDiagnosisEnum.ROUND_TYPE;
            }
        }else if(diffShoulderButtockSpace >= 3) { //모래시계가 불가한 삼각형

            if(myShoulderDiffSpace <= myWaistDiffSpace){ //직사각형 + 삼각형 가능성
                bodyDiagnosisEnum = BodyDiagnosisEnum.RECTANGLE_TRIANGLE_TYPE;
            }else { //완벽한 삼각형(허리가 얇을때)
                bodyDiagnosisEnum = BodyDiagnosisEnum.TRIANGLE_TYPE;
            }
        }else if(diffShoulderButtockSpace < 0) { //역삼각형
            bodyDiagnosisEnum = BodyDiagnosisEnum.INVERT_TRIANGLE_TYPE;
        }else { // 어깨와 힙 차이가 0 : 직사각형
            bodyDiagnosisEnum = BodyDiagnosisEnum.RECTANGLE_TYPE;
        }


        //세개의 신체가 평균 이하라면 직선형이 추가




        return bodyDiagnosisEnum;
    }


    /**
     * 1. 어깨와 힙의 밸런스를 확인
     *     1. 어깨 = 힙 : 역삼각형, 직사각형, 직선형, 타원형
     *         1. 힙 - 허리 = val
     *             - val > 14 : 역삼각형
     *             - val > 11 ~ 13 : 직사각형(1번이 평균 이하에서 차이나면 직선형)
     *             - val < 10 : 타원형
     *     2. 어깨 < 힙 : 역삼각형을 제외, (2칸까지 모래시계, 3칸부터 삼각형)
     *         - 어깨와 힙 차이 2칸까지 (힙 - 허리 = val)
     *             - val > 14~16 : 모래시계
     *             - val > 11 ~ 13 : 직사각형
     *             - val < 10 : 타원형
     *         - 어깨와 힙 차이 3칸까지 (어깨와 허리 비교)
     *             - 어깨 = 허리  or 어깨 < 허리 : 직사각형 + 삼각형 가능성
     *             - 어깨 > 허리 : 완벽한 삼각형(허리가 얇을때)
     *     3. 어깨 > 힙 : 역삼각형, 직사각형, 직선형, 타원형
     *         1. 힙 - 허리 = val
     *             - val > 14 : 역삼각형
     *             - val > 11 ~ 13 : 직사각형(1번이 평균 이하에서 차이나면 직선형)
     *             - val < 10 : 타원형
     * */
    public BodyDiagnosisEnum calculateThreeAreaCombinationMan(BodyDiagnosisDto bodyDiagnosisDto) {



    }




}
