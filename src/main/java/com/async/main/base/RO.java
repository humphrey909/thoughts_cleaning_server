package com.async.main.base;

import com.async.main.base.config.Constant;
import com.async.main.base.config.factory.ErrorMessage;
import com.async.main.base.config.factory.ErrorMessageFactory;
import com.async.main.base.config.factory.ErrorMessageType;
// import com.dnx.xouchcare.db.vo.meta.MTErrorMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  Response Object for JSON
 */
public class RO {
    private static final String RESULT_OK = "OK";
    private static final String RESULT_FAIL = "FAIL";

    @JsonProperty(value = "result")
    private String result;

    @JsonProperty(value = "error_code")
    private String errorCode;

    @JsonProperty(value = "error_message")
    private String errorMessage;

    public RO() {
        init(Constant.DEFAULT_LANG, ErrorMessageType.OK);
    }

    public RO(String lang) {
        init(lang, ErrorMessageType.OK);
    }

    public RO(ErrorMessageType type) {
        init(Constant.DEFAULT_LANG, type);
    }

    public RO(String lang, ErrorMessageType type) {
        init(lang, type);
    }

    private void init(String lang, ErrorMessageType type) {
        ErrorMessage msg =  ErrorMessageFactory.getMessage(lang, type);

        this.result = msg.isOKMessage() ? RO.RESULT_OK : RO.RESULT_FAIL;
        this.errorCode = msg.getCode();
        this.errorMessage = msg.getMessage();
    }

    public static RO getInstance(String _lang, String errorCode) {
        ErrorMessageType errorMessageType = ErrorMessageType.getByCode(errorCode);
        if (errorMessageType == null) {
            RO ret = new RO();

            ret.result = RO.RESULT_FAIL;
            ret.errorCode = errorCode;
            ret.errorMessage = String.format("ERROR %s:%s", errorCode, _lang);

            return ret;
        } else {
            return new RO(_lang, errorMessageType);
        }
    }

    // public static RO getInstance(String _lang, MTErrorMessage err) {
    //     RO ret = new RO();

    //     ret.result = err.isOK() ? RO.RESULT_OK : RO.RESULT_FAIL;
    //     ret.errorCode = Util.nvl(err.getOldErrorCode(), err.getErrorCode());
    //     ret.errorMessage = err.getMessage(_lang);

    //     return ret;
    // }
}
