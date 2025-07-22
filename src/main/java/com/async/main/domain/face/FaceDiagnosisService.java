package com.async.main.domain.face;

import com.async.main.domain.face.dto.FaceDiagnosisDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 클래스 전체에 읽기 전용 트랜잭션 적용
public class FaceDiagnosisService {


    /**
     * 얼굴 형 계산
     */
    @Transactional // 클래스 레벨의 readOnly 설정을 덮어쓰기 위해 별도 어노테이션 추가
    public FaceDiagnosisEnum calculateFaceType(FaceDiagnosisDto faceDiagnosisDto) {

        FaceDiagnosisEnum faceBasicType = FaceDiagnosisEnum.DEFAULT;

        int var1 = faceDiagnosisDto.getFaceWidth()/2 + faceDiagnosisDto.getFaceWidth(); //첫번째 계산

        if(var1 < 0){ //긴형 확실
            faceBasicType = FaceDiagnosisEnum.LONG_TYPE;
        }else if(var1 > 0){ // 계란 or 둥근
            int var2 = var1 - faceDiagnosisDto.getFaceHeight(); //첫번째 계산
            if(var2 >= 0 && var2 <= 1){ //계란 ~ 긴
                faceBasicType = FaceDiagnosisEnum.EGG_LONG_TYPE;
            }else if(var2 > 1 && var2 <= 2.9){ //계란
                faceBasicType = FaceDiagnosisEnum.EGG_TYPE;
            }else if(var2 >= 3){ //둥근
                faceBasicType = FaceDiagnosisEnum.ROUND_TYPE;
            }
        }

        return faceBasicType;
    }
}
