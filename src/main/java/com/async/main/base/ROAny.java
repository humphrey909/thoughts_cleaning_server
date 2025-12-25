package com.async.main.base;

import com.async.main.base.config.factory.ErrorMessageType;
import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.HashMap;
import java.util.Map;

public class ROAny extends RO {
    private Map<String, Object> field = null;

    public ROAny() {
        super();
    }

    public ROAny(String lang) {
        super(lang);
    }

    public ROAny(ErrorMessageType type) {
        super(type);
    }

    public ROAny(String lang, ErrorMessageType type) {
        super(lang, type);
    }

    @JsonAnyGetter
    public Map<String, Object> getField() {
        return field;
    }

    public void put(String k, Object v) {
        if (this.field == null) {
            this.field = new HashMap<>();
        }

        this.field.put(k, v);
    }
}
