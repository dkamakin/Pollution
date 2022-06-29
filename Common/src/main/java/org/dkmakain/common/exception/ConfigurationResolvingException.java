package org.dkmakain.common.exception;

public class ConfigurationResolvingException extends CommonException {

    public ConfigurationResolvingException(Throwable cause, String message, Object... params) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, cause, message, params);
    }
}
