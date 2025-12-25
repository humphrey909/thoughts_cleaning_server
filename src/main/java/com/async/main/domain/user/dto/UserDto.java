package com.async.main.domain.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private String loginId;
    private String password;
    private String loginType;
    private String fcmId;
    private String appOsType;
    private String osVersion;
    private String appVersion;
}

