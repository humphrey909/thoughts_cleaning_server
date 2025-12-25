package com.async.main.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class SocialKakaoLoginRequestDto {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("access_expires")
    private String accessExpires;

    @JsonProperty("refresh_expires")
    private String refreshExpires;

    @JsonProperty("kakao_user_id")
    private String kakaoUserId;

    @JsonProperty("profile")
    private Map<String, Object> profile; // 카카오 Account 객체를 유연하게 받기 위함

    @JsonProperty("app_device_id") // Constants.KEY_APP_DEVICE_ID (추정: 앱/기기 ID)
    private String appId;

    @JsonProperty("model_name") // Constants.KEY_MODEL_NAME (모델명)
    private String modelName;

    @JsonProperty("os_version") // Constants.KEY_OS_VERSION (OS 버전)
    private String phoneOSVersion;
}

