package com.async.main.base.config.factory;

import com.async.main.base.config.Constant;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ErrorMessageFactory {
    private static final Map<String, Map<ErrorMessageType, ErrorMessage>> _messages = new HashMap<>();
    private static final Map<String, Class> messageClassInfo = new HashMap<>();

    public ErrorMessageFactory() {
        log.warn("### START ERROR MESSAGE INIT");

        Map<ErrorMessageType, ErrorMessage> _messageEN = new EnumMap<>(ErrorMessageType.class);
        Map<ErrorMessageType, ErrorMessage> _messageES = new EnumMap<>(ErrorMessageType.class);
        Map<ErrorMessageType, ErrorMessage> _messageKO = new EnumMap<>(ErrorMessageType.class);
        Map<ErrorMessageType, ErrorMessage> _messageJA = new EnumMap<>(ErrorMessageType.class);

        _messages.put(Constant.LANG_EN, _messageEN);
        _messages.put(Constant.LANG_ES, _messageES);
        _messages.put(Constant.LANG_JA, _messageJA);
        _messages.put(Constant.LANG_KO, _messageKO);

        messageClassInfo.put(Constant.LANG_EN, MessageEN.class);
        messageClassInfo.put(Constant.LANG_ES, MessageES.class);
        messageClassInfo.put(Constant.LANG_JA, MessageJA.class);
        messageClassInfo.put(Constant.LANG_KO, MessageKO.class);
    }

    @PostConstruct
    public void init() {
        try {
            for (ErrorMessageType errorMessageType : ErrorMessageType.values()) {
                initMessage(errorMessageType);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.warn("### END ERROR MESSAGE INIT");
    }

    private static void initMessage(ErrorMessageType type) throws Exception {
        String fieldName = type.name();

        for (String lang : _messages.keySet()) {
            Field message = messageClassInfo.get(lang).getDeclaredField(fieldName);

            message.setAccessible(true);

            _messages.get(lang).put(type, new ErrorMessage(type, (String)message.get(null)));
        }
    }

    private static class MessageEN {
        private static final String OK = "OK";

        private static final String OK_MSG_CONFIRM_CERT = "The verification code has been confirmed.";
        private static final String OK_MSG_REGISTER_USER = "The registration has been completed.";

        private static final String OK_ADD_LOC_RESERVE = "setting of reservation is added.";
        private static final String OK_CHANGE_PASSWORD = "Your password have been changed.";
        private static final String OK_MODIFY_USER = "user information is changed.";
        private static final String OK_REMOVE_USER = "user is removed.";
        private static final String OK_REQ_CERT_SMS = "The verification code has been sent.";
        private static final String OK_REGISTER_DEVICE = "Your registration was successful.";
        private static final String OK_UNREGISTER_DEVICE = "succeed in unregistering device.";
        private static final String OK_UPDATE_LOC_RESERVE = "setting of reservation is changed.";

        private static final String ERROR = "An error occurred(ER000)";
        private static final String DB_ERROR = "An error occurred(ER001)";
        private static final String SYSTEM_ERROR = "An error occurred(ER002)";
        private static final String DEPRECATE_ERROR = "Old API(ER003)";
        private static final String NEED_APP_UPDATE = "You must update the app.";
        private static final String TEMPORARY_UNAVAILABLE = "This feature is temporary unavailable.";

        private static final String NO_USER = "User does not exist.";
        private static final String NO_PARAM = "An error occurred(NO_PARAM)";
        private static final String NO_DEVICE = "invalid product number nor phone number";
        private static final String NO_VTS = "there is no suni message";
        private static final String NO_CONTENT = "there is no content";
        private static final String NO_CATEGORY = "there is no category";

        private static final String ALREADY_REGISTERED_DEVICE = "Device is already registered.";
        private static final String ALREADY_REGISTERED_USER = "Phone number is already registered.";
        private static final String ALREADY_REGISTERED_TAG = "Tag is already registered.";
        private static final String EXPIRED_CERT_NO = "The verification code has expired.";
        private static final String INVALID_CERT_NO = "The verification code is incorrect.\nPlease try again.";
        private static final String INVALID_DEVICE = "Invalid device";
        private static final String INVALID_LANG = "Invalid language";
        private static final String INVALID_PARAM = "Invalid parameter";
        private static final String INVALID_SESSION_KEY = "Invalid session";
        private static final String INVALID_VTS_CATEGORY = "Invalid vts category";
        private static final String MISSING_DEVICE = "lost device";
        private static final String PASSWORD_MISMATCH = "The password is incorrect.";
        private static final String SAFEZONE_30MIN = "too close safezone within 30 minutes.";
        private static final String SAFEZONE_LIMIT = "too many safezone.";
        private static final String SESSION_KEY_EXPIRED = "Session has expired.\nPlease login again.";
        private static final String UNAUTHORIZED_USER = "Unauthorized user.";
        private static final String SMS_ALREADY_EXCEED_AMOUNT = "SMS daily limit reached.(5)";
        private static final String SMS_ALREADY_REQUESTED = "SMS already requested.\nPlease try again in 10 seconds";
        private static final String SMS_INVALID_TARGET = "It is unavailable to send SMS.";

        private static final String SMS_INVALID_TARGET_2 = "Number that already has an account.";

        // touchcare
        private static final String OK_TC_REGISTER_TAG = "succeed in registering tag.";
        private static final String OK_TC_UNREGISTER_TAG = "succeed in unregistering tag.";

        private static final String TC_NO_TAG = "invalid tag id";

        private static final String TC_ALREADY_REGISTERED_TAG = "already registered device";
        private static final String TC_UNAUTHORIZED_DEVICE = "unauthorized device.";
        private static final String TC_INVALID_MAC_ADDRESS = "invalid mac address.";

        // touchmedi
        private static final String CAN_NOT_MODIFY_YET = "you can not modify yet.";
        private static final String TM_NO_PRIMARY_PILL = "there is no primary pill.";

        // touchlog
        private static final String TL_INVALID_GROUP = "there is no group.";
        private static final String TL_NO_MORE_DEVICE = "you cannot register no more device.";

        // xouchcare
        private static final String XC_ALREADY_JOINED_CONTACT = "You have already registered your device.";
        private static final String XC_ALREADY_OWNER = "you have already registered device.";
        private static final String XC_IMPOSSIBLE_TARGET = "The target is impossible.";
        private static final String XC_INVALID_LOGIN_ID = "Invalid login ID";
        private static final String XC_INVALID_TAG = "Invalid tag";
        private static final String XC_INVALID_USER = "Invalid  user";
        private static final String XC_IOS_INVALID_USER = "Unavailable for iOS user";
        private static final String XC_JOIN_REQUEST_FAIL = "Registration request cannot be sent to the user.";
        private static final String XC_LOGIN_FAIL = "Invalid user or password";
        private static final String XC_MUST_OVER_18_YO = "Must be over 18 years old.";
        private static final String XC_NO_BEHAVIOR = "no behavior";
        private static final String XC_NO_CONTACT = "Unregistered user";

        private static final String XC_ALREADY_JOINED_CONTACT_USER_CONNECT = "\n" +
                "You are already a registered user.\n" +
                "Check your phone number again or\n" +
                "Please create your own ID instead!";
        private static final String XC_NO_USER_USER_CONNECT = "User does not exist.\n" +
                "Check your phone number again or\n" +
                "Please create your own ID instead!";
        private static final String XC_JOIN_REQUEST_FAIL_USER_CONNECT = "Registration request cannot be sent to the user.";



        // masil
        private static final String NO_ON_AIR_BROADCAST = "There is no broadcast on air.";
        private static final String SHOP_NO_PRODUCT = "There is no product.";
        private static final String SHOP_NO_STOCK = "There is no stock.";
        private static final String SHOP_ALREADY_ORDER = "이미 접수된 상품입니다.\n순이가 확인하고\n연락드릴게요!";

        // external
        private static final String EX_TOKEN_EXPIRED = "Auth token has expired.";

        // kakao
        private static final String KAKAO_AUTH_FAIL = "fail to kakao auth.";

        private static final String DUPLICATION_DATA = "An error duplication data(DUPLICATION_DATA)";

        private static final String IOS_DVICE_CANCEL = "User membership cannot be registered with IOS devices. ";


        private static final String XC_ALREADY_EMERGENCY_ING = "already emergency mode.";
        private static final String XC_NOT_START_EMERGENCY = "not start emergency mode.";

        private static final String XC_EMERGENCY_LOGOUT_STATE = "logout state, gps not work.";

        private static final String VIDEO_CHATTING_ERROR = "잠시후 다시 이용해주세요.";
        private static final String VIDEO_CHATTING_ROOM_MAX = "최대 인원이 초과되었어요. \n 다른 방에서 대화 나눠요!";
        private static final String VIDEO_CHATTING_ROOM_ALREADY_DELETED = "아쉽지만 다른 방으로 입장해주세요.";
        private static final String VIDEO_CHATTING_ROOM_SOON_DELETED = "아쉽지만 다른 방으로 입장해주세요.";
        private static final String VIDEO_CHATTING_ROOM_EXIST = "아쉽지만 다른 방으로 입장해주세요.";
        private static final String VIDEO_CHATTING_ROOM_RECORDING = "아쉽지만 다른 방으로 입장해주세요.";

        private static final String QUIZ_WRONG_ACCESS = "잘못된 접근입니다. ";
        private static final String QUIZ_NO_CORRECT_ACCESS = "틀린 답변 입니다. ";
        private static final String QUIZ_FINISH_OF_DAY = "오늘 문제가 종료되었습니다. ";

    }

    private static class MessageJA {
        private static final String OK = "処理完了";

        private static final String OK_MSG_CONFIRM_CERT = "認証番号が確認されました。";
        private static final String OK_MSG_REGISTER_USER = "新規登録が完了しました。";

        private static final String OK_ADD_LOC_RESERVE = "位置リクエスト予約の設定が追加されました。";
        private static final String OK_CHANGE_PASSWORD = "パスワードが変更されました。";
        private static final String OK_MODIFY_USER = "ユーザー情報が変更されました。";
        private static final String OK_REMOVE_USER = "ユーザー情報が削除されました。";
        private static final String OK_REQ_CERT_SMS = "認証番号を送信しました。";
        private static final String OK_REGISTER_DEVICE = "接続が完了しました。";
        private static final String OK_UNREGISTER_DEVICE = "端末との接続が解除されました。";
        private static final String OK_UPDATE_LOC_RESERVE = "位置リクエスト予約の設定が変更されました。";

        private static final String ERROR = "エラーが発生しました。(ER000)";
        private static final String DB_ERROR = "エラーが発生しました。(ER001)";
        private static final String SYSTEM_ERROR = "エラーが発生しました。(ER002)";
        private static final String DEPRECATE_ERROR = "古いAPIです。(ER003)";
        private static final String NEED_APP_UPDATE = "アプリを更新する必要があります。";
        private static final String TEMPORARY_UNAVAILABLE = "しばらくこの機能を使用することができません。";

        private static final String NO_USER = "ユーザーが存在しません。";
        private static final String NO_PARAM = "エラーが発生しました。(SWER001)";
        private static final String NO_DEVICE = "製品番号または電話番号が正しくありません。";
        private static final String NO_VTS = "メッセージはありません。";
        private static final String NO_CONTENT = "there is no content";
        private static final String NO_CATEGORY = "there is no category";

        private static final String ALREADY_REGISTERED_DEVICE = "既に登録されている端末です。";
        private static final String ALREADY_REGISTERED_USER = "既に登録されている電話番号です。";
        private static final String ALREADY_REGISTERED_TAG = "既に追加されたタグです。";
        private static final String EXPIRED_CERT_NO = "認証番号の期限が切れました。";
        private static final String INVALID_CERT_NO = "認証番号が一致しません。もう一度確認してください。";
        private static final String INVALID_DEVICE = "Invalid device";
        private static final String INVALID_LANG = "Invalid language";
        private static final String INVALID_PARAM = "無効な入力値です。";
        private static final String INVALID_SESSION_KEY = "他の機器からログインしたため、再度ログインしてください。";
        private static final String INVALID_VTS_CATEGORY = "invalid vts category";
        private static final String MISSING_DEVICE = "紛失されている端末です。";
        private static final String PASSWORD_MISMATCH = "パスワードが正しくありません。";
        private static final String SAFEZONE_30MIN = "30分以内にセーフゾーンが登録されています。";
        private static final String SAFEZONE_LIMIT = "セーフゾーン登録制限を超えました。";
        private static final String SESSION_KEY_EXPIRED = "一定時間が経過したため、再度ログインしてください。";
        private static final String UNAUTHORIZED_USER = "権限がありません。";
        private static final String SMS_ALREADY_EXCEED_AMOUNT = "SMSを要請できる一日の回数を超えました。(5)";
        private static final String SMS_ALREADY_REQUESTED = "SMSは既に要求されました。 10秒後に再試行してください。";
        private static final String SMS_INVALID_TARGET = "SMSを送信することができない対象です。";

        private static final String SMS_INVALID_TARGET_2 = "すでにアカウントがある番号です。";

        // touchcare
        private static final String OK_TC_REGISTER_TAG = "接続が完了しました。";
        private static final String OK_TC_UNREGISTER_TAG = "TAGとの接続が解除されました。";

        private static final String TC_NO_TAG = "TAG IDが正しくありません。";

        private static final String TC_ALREADY_REGISTERED_TAG = "既に登録されているTAGです。";
        private static final String TC_UNAUTHORIZED_DEVICE = "権限がありません。";
        private static final String TC_INVALID_MAC_ADDRESS = "無効なMAC ADDRESSです。";

        // touchmedi
        private static final String CAN_NOT_MODIFY_YET = "you can not modify yet.";
        private static final String TM_NO_PRIMARY_PILL = "there is no primary pill.";

        // touchlog
        private static final String TL_INVALID_GROUP = "グループが存在しません。";
        private static final String TL_NO_MORE_DEVICE = "これ以上、デバイスを登録することができません。";

        // xouchcare
        private static final String XC_ALREADY_JOINED_CONTACT = "既に登録されているユーザーです。";
        private static final String XC_ALREADY_OWNER = "既にデバイスは登録されています。";
        private static final String XC_IMPOSSIBLE_TARGET = "その対象は不可能です。";
        private static final String XC_INVALID_LOGIN_ID = "無効なログインIDです。";
        private static final String XC_INVALID_TAG = "無効なTAGです。";
        private static final String XC_INVALID_USER = "無効なユーザーです。";
        private static final String XC_IOS_INVALID_USER = "IOSでは不可能なユーザーです。";
        private static final String XC_JOIN_REQUEST_FAIL = "ユーザーに登録リクエストを送信することができません。";
        private static final String XC_LOGIN_FAIL = "ログインIDまたはパスワードが間違っています。";
        private static final String XC_MUST_OVER_18_YO = "18歳以上である必要があります。";
        private static final String XC_NO_BEHAVIOR = "存在しない行動です。";
        private static final String XC_NO_CONTACT = "登録されていないユーザーです。";

        private static final String XC_ALREADY_JOINED_CONTACT_USER_CONNECT = "登録済みユーザーです。\n" +
                "電話番号をもう一度確認したり\n" +
                "ユーザーのIDを代わりに作ってください！";
        private static final String XC_NO_USER_USER_CONNECT = "ユーザーが存在しません。\n" +
                "電話番号をもう一度確認したり\n" +
                "ユーザーのIDを代わりに作ってください！";
        private static final String XC_JOIN_REQUEST_FAIL_USER_CONNECT = "ユーザーがログアウト状態のため、登録リクエストを送信できません。\n" +
                "ユーザーの携帯電話でアプリのログインを進めてください。";



        // masil
        private static final String NO_ON_AIR_BROADCAST = "There is no broadcast on air.";
        private static final String SHOP_NO_PRODUCT = "There is no product.";
        private static final String SHOP_NO_STOCK = "There is no stock.";
        private static final String SHOP_ALREADY_ORDER = "이미 접수된 상품입니다.\n순이가 확인하고\n연락드릴게요!";

        // external
        private static final String EX_TOKEN_EXPIRED = "トークンの有効期限が切れました。";

        // kakao
        private static final String KAKAO_AUTH_FAIL = "fail to kakao auth.";

        private static final String DUPLICATION_DATA = "An error duplication data(DUPLICATION_DATA)";

        private static final String IOS_DVICE_CANCEL = "User membership cannot be registered with IOS devices. ";

        private static final String XC_ALREADY_EMERGENCY_ING = "already emergency mode.";
        private static final String XC_NOT_START_EMERGENCY = "not start emergency mode.";

        private static final String XC_EMERGENCY_LOGOUT_STATE = "logout state, gps not work.";

        private static final String VIDEO_CHATTING_ERROR = "잠시후 다시 이용해주세요.";
        private static final String VIDEO_CHATTING_ROOM_MAX = "최대 인원이 초과되었어요. \n 다른 방에서 대화 나눠요!";
        private static final String VIDEO_CHATTING_ROOM_ALREADY_DELETED = "아쉽지만 다른 방으로 입장해주세요.";
        private static final String VIDEO_CHATTING_ROOM_SOON_DELETED = "아쉽지만 다른 방으로 입장해주세요.";
        private static final String VIDEO_CHATTING_ROOM_EXIST = "아쉽지만 다른 방으로 입장해주세요.";
        private static final String VIDEO_CHATTING_ROOM_RECORDING = "아쉽지만 다른 방으로 입장해주세요.";

        private static final String QUIZ_WRONG_ACCESS = "잘못된 접근입니다. ";
        private static final String QUIZ_NO_CORRECT_ACCESS = "틀린 답변 입니다. ";
        private static final String QUIZ_FINISH_OF_DAY = "오늘 문제가 종료되었습니다. ";

    }

    private static class MessageKO {
        private static final String OK = "처리 성공.";

        private static final String OK_MSG_CONFIRM_CERT = "인증번호가 확인되었습니다.";
        private static final String OK_MSG_REGISTER_USER = "신규 가입이 완료되었습니다.";

        private static final String OK_ADD_LOC_RESERVE = "위치요청 예약 설정이 추가되었습니다.";
        private static final String OK_CHANGE_PASSWORD = "비밀번호가 변경되었습니다.";
        private static final String OK_MODIFY_USER = "사용자 정보가 변경되었습니다.";
        private static final String OK_REMOVE_USER = "사용자 정보가 삭제되었습니다.";
        private static final String OK_REQ_CERT_SMS = "인증번호가 발송되었습니다.";
        private static final String OK_REGISTER_DEVICE = "연결이 완료되었습니다.";
        private static final String OK_UNREGISTER_DEVICE = "단말기와의 연결이 해제되었습니다.";
        private static final String OK_UPDATE_LOC_RESERVE = "위치요청 예약 설정이 변경되었습니다.";

        private static final String ERROR = "오류가 발생하였습니다.(ER000)";
        private static final String DB_ERROR = "오류가 발생하였습니다.(ER001)";
        private static final String SYSTEM_ERROR = "오류가 발생하였습니다.(ER002)";
        private static final String DEPRECATE_ERROR = "오래된 API입니다.(ER003)";
        private static final String NEED_APP_UPDATE = "앱을 업데이트 해야 합니다.";
        private static final String TEMPORARY_UNAVAILABLE = "당분간 이 기능을 사용할 수 없습니다.";

        private static final String NO_USER = "사용자가 존재하지 않습니다.";
        private static final String NO_PARAM = "오류가 발생하였습니다.(SWER001)";
        private static final String NO_DEVICE = "제품번호 또는 전화번호가 바르지 않습니다.";
        private static final String NO_VTS = "메시지가 없습니다.";
        private static final String NO_CONTENT = "컨텐츠가 없습니다.";
        private static final String NO_CATEGORY = "카테고리가 없습니다.";

        private static final String ALREADY_REGISTERED_DEVICE = "이미 등록된 단말입니다.";
        private static final String ALREADY_REGISTERED_USER = "이미 가입된 전화번호입니다.";
        private static final String ALREADY_REGISTERED_TAG = "이미 추가된 태그입니다.";
        private static final String EXPIRED_CERT_NO = "인증번호가 만료되었습니다.";
        private static final String INVALID_CERT_NO = "인증번호가 일치하지 않습니다. 다시 한번 확인해 주세요.";
        private static final String INVALID_DEVICE = "잘못된 디바이스입니다.";
        private static final String INVALID_LANG = "잘못된 언어입니다.";
        private static final String INVALID_PARAM = "잘못된 입력 값입니다.";
        private static final String INVALID_SESSION_KEY = "다른 기기에서 로그인하여 재로그인이 필요합니다.";
        private static final String INVALID_VTS_CATEGORY = "잘못된 메시지 카테고리입니다.";
        private static final String MISSING_DEVICE = "분실신고 중인 단말입니다.";
        private static final String PASSWORD_MISMATCH = "비밀번호가 바르지 않습니다.";
        private static final String SAFEZONE_30MIN = "30분 이내에 세이프존이 등록되어 있습니다.";
        private static final String SAFEZONE_LIMIT = "세이프존 등록한도를 초과하였습니다.";
        private static final String SESSION_KEY_EXPIRED = "일정 시간이 경과하여 재로그인이 필요합니다.";
        private static final String UNAUTHORIZED_USER = "권한이 없습니다.";
        private static final String SMS_ALREADY_EXCEED_AMOUNT = "sms를 요청할 수 있는 하루 횟수를 초과하였습니다. (5)";
        private static final String SMS_ALREADY_REQUESTED = "sms가 이미 요청되었습니다. 10초 후에 다시 시도해주세요";
        private static final String SMS_INVALID_TARGET = "SMS를 보낼 수 없는 대상입니다.";

        private static final String SMS_INVALID_TARGET_2 = "이미 계정이 있는 번호입니다.";

        // touchcare
        private static final String OK_TC_REGISTER_TAG = "연결이 완료되었습니다.";
        private static final String OK_TC_UNREGISTER_TAG = "TAG와의 연결이 해제되었습니다.";

        private static final String TC_NO_TAG = "TAG ID가 올바르지 않습니다.";

        private static final String TC_ALREADY_REGISTERED_TAG = "이미 등록된 TAG입니다.";
        private static final String TC_UNAUTHORIZED_DEVICE = "권한이 없습니다.";
        private static final String TC_INVALID_MAC_ADDRESS = "잘못된 MAC ADDRESS 입니다.";

        // touchmedi
        private static final String CAN_NOT_MODIFY_YET = "아직은 변경할 수 없습니다.";
        private static final String TM_NO_PRIMARY_PILL = "등록된 대표약이 없습니다.";

        // touchlog
        private static final String TL_INVALID_GROUP = "그룹이 존재하지 않습니다.";
        private static final String TL_NO_MORE_DEVICE = "더 이상의 디바이스를 등록할 수 없습니다.";

        // xouchcare
        private static final String XC_ALREADY_JOINED_CONTACT = "이미 등록된 사용자입니다.";
        private static final String XC_ALREADY_OWNER = "이미 디바이스를 등록했습니다.";
        private static final String XC_IMPOSSIBLE_TARGET = "그 대상은 불가능합니다.";
        private static final String XC_INVALID_LOGIN_ID = "잘못된 로그인 아이디입니다.";
        private static final String XC_INVALID_TAG = "잘못된 TAG입니다.";
        private static final String XC_INVALID_USER = "잘못된 사용자입니다.";
        private static final String XC_IOS_INVALID_USER = "IOS에서는 불가능한 사용자입니다.";
        private static final String XC_JOIN_REQUEST_FAIL = "사용자가 로그아웃 상태이므로 등록 요청을 보낼 수 없습니다.";
        private static final String XC_LOGIN_FAIL = "로그인 아이디 혹은 패스워드가 잘못되었습니다.";
        private static final String XC_MUST_OVER_18_YO = "18세 이상이어야 합니다.";
        private static final String XC_NO_BEHAVIOR = "존재하지 않는 행동입니다.";
        private static final String XC_NO_CONTACT = "등록하지 않은 사용자 입니다.";

        private static final String XC_ALREADY_JOINED_CONTACT_USER_CONNECT = "이미 등록된 사용자입니다.\n전화번호를 다시 확인하거나\n화면을 빠져나가주세요.";
        private static final String XC_NO_USER_USER_CONNECT = "사용자가 존재하지 않습니다.\n전화번호를 다시 확인하거나\n사용자의 아이디를 대신 만들어주세요!";
        private static final String XC_JOIN_REQUEST_FAIL_USER_CONNECT = "사용자가 로그아웃 상태이므로 등록 요청을 보낼 수 없습니다.\n사용자 휴대폰에서 앱 로그인을 진행해주세요.";


        // masil
        private static final String NO_ON_AIR_BROADCAST = "진행 중인 방송이 없습니다.";
        private static final String SHOP_NO_PRODUCT = "해당 상품이 없습니다.";
        private static final String SHOP_NO_STOCK = "상품의 재고가 없습니다.";
        private static final String SHOP_ALREADY_ORDER = "이미 접수된 상품입니다.\n순이가 확인하고\n연락드릴게요!";

        // external
        private static final String EX_TOKEN_EXPIRED = "토큰 유효기간이 만료되었습니다.";

        // kakao
        private static final String KAKAO_AUTH_FAIL = "카카오 계정 인증에 실패했습니다.";

        private static final String DUPLICATION_DATA = "데이터 중복 에러 입니다.";

        private static final String IOS_DVICE_CANCEL = "IOS 기기로는 사용자 회원가입을 진행할 수 없습니다. ";

        private static final String XC_ALREADY_EMERGENCY_ING = "이미 실행중이며 시작 시점으로부터\n" +
                "1시간 후 자동으로 종료됩니다.";
        private static final String XC_NOT_START_EMERGENCY = "긴급모드가 시작되지 않았습니다.\n" +
                "다시 시도해주세요.";

        private static final String XC_EMERGENCY_LOGOUT_STATE = "사용자가 로그아웃 상태이므로\n" +
                "위치를 확인할 수 없습니다.";

        private static final String VIDEO_CHATTING_ERROR = "잠시후 다시 이용해주세요.";
        private static final String VIDEO_CHATTING_ROOM_MAX = "최대 인원이 초과되었어요. \n 다른 방에서 대화 나눠요!";
        private static final String VIDEO_CHATTING_ROOM_ALREADY_DELETED = "아쉽지만 다른 방으로 입장해주세요.";
        private static final String VIDEO_CHATTING_ROOM_SOON_DELETED = "아쉽지만 다른 방으로 입장해주세요.";
        private static final String VIDEO_CHATTING_ROOM_EXIST = "아쉽지만 다른 방으로 입장해주세요.";
        private static final String VIDEO_CHATTING_ROOM_RECORDING = "아쉽지만 다른 방으로 입장해주세요.";

        private static final String QUIZ_WRONG_ACCESS = "잘못된 접근입니다. ";
        private static final String QUIZ_NO_CORRECT_ACCESS = "틀린 답변 입니다. ";
        private static final String QUIZ_FINISH_OF_DAY = "오늘 문제가 종료되었습니다. ";
    }

    private static class MessageES {
        private static final String OK = "OK";

        private static final String OK_MSG_CONFIRM_CERT = "El código de verificación ha sido confirmado.";
        private static final String OK_MSG_REGISTER_USER = "El registro ha sido completado.";

        private static final String OK_ADD_LOC_RESERVE = "위치요청 예약 설정이 추가되었습니다.";
        private static final String OK_CHANGE_PASSWORD = "Tu contraseña ha sido restablecida.";
        private static final String OK_MODIFY_USER = "사용자 정보가 변경되었습니다.";
        private static final String OK_REMOVE_USER = "사용자 정보가 삭제되었습니다.";
        private static final String OK_REQ_CERT_SMS = "Código de verificación enviado.";
        private static final String OK_REGISTER_DEVICE = "Tu registro ha sido exitoso. ";
        private static final String OK_UNREGISTER_DEVICE = "Tener éxito en registrar el dispositivo.";
        private static final String OK_UPDATE_LOC_RESERVE = "위치요청 예약 설정이 변경되었습니다.";

        private static final String ERROR = "Se ha producido un error(ER000)";
        private static final String DB_ERROR = "Se ha producido un error(ER001)";
        private static final String SYSTEM_ERROR = "Se ha producido un error(ER002)";
        private static final String DEPRECATE_ERROR = "API antigua(ER003)";
        private static final String NEED_APP_UPDATE = "Debes actualizar la aplicación.";
        private static final String TEMPORARY_UNAVAILABLE = "Esta función no está disponible temporalmente.";

        private static final String NO_USER = "El usuario no existe.";
        private static final String NO_PARAM = "Se ha producido un error(NO_PARAM)";
        private static final String NO_DEVICE = "제품번호 또는 전화번호가 바르지 않습니다.";
        private static final String NO_VTS = "";
        private static final String NO_CONTENT = "there is no content";
        private static final String NO_CATEGORY = "there is no category";

        private static final String ALREADY_REGISTERED_DEVICE = "El dispositivo ya está registrado.";
        private static final String ALREADY_REGISTERED_USER = "El número de teléfono ya está registrado.";
        private static final String ALREADY_REGISTERED_TAG = "La etiqueta ya está registrada.";
        private static final String EXPIRED_CERT_NO = "El código de verificación ha expirado.";
        private static final String INVALID_CERT_NO = "El código de verificación es incorrecto. Inténtalo de nuevo.";
        private static final String INVALID_DEVICE = "잘못된 디바이스입니다.";
        private static final String INVALID_LANG = "Invalid language";
        private static final String INVALID_PARAM = "Parámetro inválido.";
        private static final String INVALID_SESSION_KEY = "Sesión inválida.";
        private static final String INVALID_VTS_CATEGORY = "Invalid vts category";
        private static final String MISSING_DEVICE = "분실신고 중인 단말입니다.";
        private static final String PASSWORD_MISMATCH = "La contraseña es incorrecta.";
        private static final String SAFEZONE_30MIN = "30분 이내에 세이프존이 등록되어 있습니다.";
        private static final String SAFEZONE_LIMIT = "세이프존 등록한도를 초과하였습니다.";
        private static final String SESSION_KEY_EXPIRED = "La sesión ha caducado. Vuelve a iniciar sesión. ";
        private static final String UNAUTHORIZED_USER = "Usuario no autorizado.";
        private static final String SMS_ALREADY_EXCEED_AMOUNT = "Límite diario de SMS alcanzado (5)";
        private static final String SMS_ALREADY_REQUESTED = "SMS ya ha sido solicitado.\nPor favor, inténtalo de nuevo en 10 segundos.";
        private static final String SMS_INVALID_TARGET = "SMS를 보낼 수 없는 대상입니다.";

        private static final String SMS_INVALID_TARGET_2 = "이미 계정이 있는 번호입니다.";

        // touchcare
        private static final String OK_TC_REGISTER_TAG = "연결이 완료되었습니다.";
        private static final String OK_TC_UNREGISTER_TAG = "TAG와의 연결이 해제되었습니다.";

        private static final String TC_NO_TAG = "TAG ID가 올바르지 않습니다.";

        private static final String TC_ALREADY_REGISTERED_TAG = "이미 등록된 TAG입니다.";
        private static final String TC_UNAUTHORIZED_DEVICE = "권한이 없습니다.";
        private static final String TC_INVALID_MAC_ADDRESS = "잘못된 MAC ADDRESS 입니다.";

        // touchmedi
        private static final String CAN_NOT_MODIFY_YET = "you can not modify yet.";
        private static final String TM_NO_PRIMARY_PILL = "there is no primary pill.";

        // touchlog
        private static final String TL_INVALID_GROUP = "그룹이 존재하지 않습니다.";
        private static final String TL_NO_MORE_DEVICE = "더 이상의 디바이스를 등록할 수 없습니다.";

        // xouchcare
        private static final String XC_ALREADY_JOINED_CONTACT = "El usuario ya está registrado.";
        private static final String XC_ALREADY_OWNER = "Ya has registrado tu dispositivo.";
        private static final String XC_IMPOSSIBLE_TARGET = "El objetivo es imposible.";
        private static final String XC_INVALID_LOGIN_ID = "ID de inicio de sesión inválido.";
        private static final String XC_INVALID_TAG = "Etiqueta inválida.";
        private static final String XC_INVALID_USER = "잘못된 사용자입니다.";
        private static final String XC_IOS_INVALID_USER = "No disponible para usuarios de iOS";
        private static final String XC_JOIN_REQUEST_FAIL = "La solicitud de registro no se puede enviar al usuario. Asegúrese que el usuario tiene la sesión iniciada.";
        private static final String XC_LOGIN_FAIL = "Usuario o contraseña no válidos";
        private static final String XC_MUST_OVER_18_YO = "Debe tener más de 18 años.";
        private static final String XC_NO_BEHAVIOR = "존재하지 않는 행동입니다.";
        private static final String XC_NO_CONTACT = "Usuario no registrado.";

        private static final String XC_ALREADY_JOINED_CONTACT_USER_CONNECT = "Ya eres un usuario registrado.\n" +
                "Verifique su número de teléfono nuevamente o\n" +
                "¡Crea tu propia identificación!";
        private static final String XC_NO_USER_USER_CONNECT = "El usuario no existe.\n" +
                "Verifique su número de teléfono nuevamente o\n" +
                "¡Crea tu propia identificación!";
        private static final String XC_JOIN_REQUEST_FAIL_USER_CONNECT = "La solicitud de registro no se puede enviar porque el usuario ha cerrado sesión.\n" +
                "Inicie sesión en la aplicación en su teléfono móvil.";





        // masil
        private static final String NO_ON_AIR_BROADCAST = "진행 중인 방송이 없습니다.";
        private static final String SHOP_NO_PRODUCT = "해당 상품이 없습니다.";
        private static final String SHOP_NO_STOCK = "상품의 재고가 없습니다.";
        private static final String SHOP_ALREADY_ORDER = "이미 접수된 상품입니다.\n순이가 확인하고\n연락드릴게요!";

        // external
        private static final String EX_TOKEN_EXPIRED = "토큰 유효기간이 만료되었습니다.";

        // kakao
        private static final String KAKAO_AUTH_FAIL = "카카오 계정 인증에 실패했습니다.";

        private static final String DUPLICATION_DATA = "An error duplication data(DUPLICATION_DATA)";

        private static final String IOS_DVICE_CANCEL = "User membership cannot be registered with IOS devices. ";

        private static final String XC_ALREADY_EMERGENCY_ING = "이미 실행중이며 시작 시점으로부터\n" +
                "1시간 후 자동으로 종료됩니다.";
        private static final String XC_NOT_START_EMERGENCY = "긴급 모드가 시작되지 않았습니다. 한번 더 요청 해주세요.";

        private static final String XC_EMERGENCY_LOGOUT_STATE = "사용자가 로그아웃 상태이므로\n" +
                "위치를 확인할 수 없습니다.";


        private static final String VIDEO_CHATTING_ERROR = "잠시후 다시 이용해주세요."; //잘못된 접근입니다. 관리자에게 문의해주세요.
        private static final String VIDEO_CHATTING_ROOM_MAX = "최대 인원이 초과되었어요. \n 다른 방에서 대화 나눠요!"; //방에 최대 인원을 초과하였습니다.
        private static final String VIDEO_CHATTING_ROOM_ALREADY_DELETED = "아쉽지만 다른 방으로 입장해주세요."; //삭제된 방입니다.
        private static final String VIDEO_CHATTING_ROOM_SOON_DELETED = "아쉽지만 다른 방으로 입장해주세요."; //이 방은 곧 사라질 예정입니다.
        private static final String VIDEO_CHATTING_ROOM_EXIST = "아쉽지만 다른 방으로 입장해주세요."; //해당 방은 이미 생성된 카테고리 방 입니다.
        private static final String VIDEO_CHATTING_ROOM_RECORDING = "아쉽지만 다른 방으로 입장해주세요."; //입장 보류 중인 방입니다. 잠시 후 다시 입장해주세요.

        private static final String QUIZ_WRONG_ACCESS = "잘못된 접근입니다. ";
        private static final String QUIZ_NO_CORRECT_ACCESS = "틀린 답변 입니다. ";
        private static final String QUIZ_FINISH_OF_DAY = "오늘 문제가 종료되었습니다. ";
    }

    public static ErrorMessage getMessage(String lang, ErrorMessageType type) {
        if (!_messages.containsKey(lang)) {
            lang = Constant.DEFAULT_LANG;
        }

        return _messages.get(lang).get(type);
    }

}
