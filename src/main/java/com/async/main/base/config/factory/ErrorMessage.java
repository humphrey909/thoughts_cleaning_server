package com.async.main.base.config.factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorMessage {
    private final ErrorMessageType type;

    private final String message;

    public ErrorMessage(ErrorMessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getCode() {
        return type.getValue();
    }

    public String getMessage() {
        return message;
    }

    public boolean isOKMessage() {
        return this.type.isOK();
    }
}
