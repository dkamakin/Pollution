package org.dkmakain.common.exception;

import java.io.Serial;

public class JsonProcessingException extends CommonException {

    @Serial private static final long serialVersionUID = -3442713248165942074L;

    public JsonProcessingException(Throwable cause, String message, Object... params) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, cause, message, params);
    }
}
