package com.async.main.base.config.factory;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum ErrorMessageType {
    OK,

    OK_MSG_CONFIRM_CERT,
    OK_MSG_REGISTER_USER,

    OK_ADD_LOC_RESERVE,
    OK_CHANGE_PASSWORD,
    OK_MODIFY_USER,
    OK_REMOVE_USER,
    OK_REQ_CERT_SMS,
    OK_REGISTER_DEVICE,
    OK_UNREGISTER_DEVICE,
    OK_UPDATE_LOC_RESERVE,

    ERROR,
    DB_ERROR,
    SYSTEM_ERROR,
    DEPRECATE_ERROR,
    NEED_APP_UPDATE,
    TEMPORARY_UNAVAILABLE,

    NO_USER,
    NO_PARAM,
    NO_DEVICE,
    NO_VTS,
    NO_CONTENT,
    NO_CATEGORY,

    ALREADY_REGISTERED_DEVICE("ERR205"),
    ALREADY_REGISTERED_USER("ERR007"),
    ALREADY_REGISTERED_TAG("ERR008"),
    EXPIRED_CERT_NO("SWER102"),
    INVALID_CERT_NO("SWER101"),
    INVALID_DEVICE,
    INVALID_LANG,
    INVALID_PARAM,
    INVALID_SESSION_KEY("SWER005"),
    INVALID_VTS_CATEGORY,
    MISSING_DEVICE("SWER203"),
    PASSWORD_MISMATCH("SWER003"),
    SAFEZONE_30MIN("SWER302"),
    SAFEZONE_LIMIT("SWER301"),
    SESSION_KEY_EXPIRED("SWER004"),
    UNAUTHORIZED_USER("SWER006"),
    SMS_ALREADY_EXCEED_AMOUNT("SWER007"),
    SMS_ALREADY_REQUESTED("SWER008"),
    SMS_INVALID_TARGET("SWER009"),

    SMS_INVALID_TARGET_2("SWER0010"),


    //ios 로 접근시 디바이스 취소 알림
    IOS_DVICE_CANCEL,

    // touchcare
    OK_TC_REGISTER_TAG,
    OK_TC_UNREGISTER_TAG,

    TC_NO_TAG("TCER002"),

    TC_ALREADY_REGISTERED_TAG("TCER205"),
    TC_UNAUTHORIZED_DEVICE("TCER006"),
    TC_INVALID_MAC_ADDRESS("TCER007"),

    // touchmedi
    CAN_NOT_MODIFY_YET,
    TM_NO_PRIMARY_PILL("NO_PRIMARY_PILL"),

    // touch_log
    TL_INVALID_GROUP("TLER101"),
    TL_NO_MORE_DEVICE("TLER102"),

    // xouchcare
    XC_ALREADY_JOINED_CONTACT("XCER102"),
    XC_ALREADY_OWNER("XCER111"),
    XC_IMPOSSIBLE_TARGET("XCER103"),
    XC_INVALID_LOGIN_ID("XCER112"),
    XC_INVALID_TAG("XCER105"),
    XC_INVALID_USER("XCER116"),
    XC_IOS_INVALID_USER("XCER108"),
    XC_JOIN_REQUEST_FAIL("XCER114"),
    XC_LOGIN_FAIL("XCER113"),
    XC_MUST_OVER_18_YO("XCER115"),
    XC_NO_BEHAVIOR("XCER106"),
    XC_NO_CONTACT("XCER107"),

    XC_NO_USER_USER_CONNECT,
    XC_ALREADY_JOINED_CONTACT_USER_CONNECT("XCER109"),
    XC_JOIN_REQUEST_FAIL_USER_CONNECT("XCER109"),

    // masil
    NO_ON_AIR_BROADCAST,
    SHOP_NO_PRODUCT,
    SHOP_NO_STOCK,
    SHOP_ALREADY_ORDER,

    //
    EX_TOKEN_EXPIRED,

    //
    KAKAO_AUTH_FAIL,

    //긴급모드 이미 진행중
    XC_ALREADY_EMERGENCY_ING("XC_EMERGENCY_01"),
    //긴급모드 이미 진행중
    XC_NOT_START_EMERGENCY("XC_EMERGENCY_02"),
    //로그아웃 상태일때
    XC_EMERGENCY_LOGOUT_STATE(),


    //랜덤 콜
    //중복 에러
    DUPLICATION_DATA,

    QUIZ_WRONG_ACCESS(),
    QUIZ_NO_CORRECT_ACCESS(),
    QUIZ_FINISH_OF_DAY(),

    VIDEO_CHATTING_ERROR("CONNECT_DELAY"),
    VIDEO_CHATTING_ROOM_MAX("ROOM_MAX"),
    VIDEO_CHATTING_ROOM_ALREADY_DELETED("ROOM_DELETE"),
    VIDEO_CHATTING_ROOM_SOON_DELETED("ROOM_DELETE"),
    VIDEO_CHATTING_ROOM_EXIST("ROOM_DELETE"),
    VIDEO_CHATTING_ROOM_RECORDING("ROOM_DELETE")
    ;

    private static final Map<String, ErrorMessageType> valInfo = new HashMap<>();

    static {
        Arrays.stream(values())
                .forEach(o -> {
                    if (o != null) valInfo.put(o.name(), o);
                });
    }

    @Getter
    private final String value;

    ErrorMessageType() {
        if (this.name().startsWith("OK")) {
            this.value = "";
        } else {
            this.value = this.name();
        }
    }

    ErrorMessageType(String val) {
        this.value = val;
    }

    public boolean isOK() {
        return value.isEmpty();
    }

    public static ErrorMessageType getByCode(String errorCode) {
        return valInfo.get(errorCode);
    }
}
