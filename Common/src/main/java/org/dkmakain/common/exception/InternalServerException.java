package org.dkmakain.common.exception;

public class InternalServerException extends CommonException {

    public InternalServerException(Throwable cause, String message, Object... params) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, cause, message, params);
    }

    public InternalServerException(String message, Object... params) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, message, params);
    }
}
