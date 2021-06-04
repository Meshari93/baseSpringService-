package com.app.enums;

public enum MessageKeys {
    SUCCESS(100, "success"),
    INTERNAL_SERVER_ERROR(100, "internal_server_error");


    private final int code;
    private final String key;

    MessageKeys(int code, String key) {
        this.code = code;
        this.key = key;
    }

    public final int getCode() {
        return code;
    }
    public final String getKey(){
        return key;
    }
}
