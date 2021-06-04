package com.app.exceptions;

import com.app.enums.MessageKeys;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = -1799524404365612629L;
    private final MessageKeys messageKeys;

    public ValidationException(MessageKeys keys) {
        super(keys.getKey());
        this.messageKeys = keys;
    }
}
