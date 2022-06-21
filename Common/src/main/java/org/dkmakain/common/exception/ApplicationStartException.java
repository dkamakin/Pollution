package org.dkmakain.common.exception;

import java.io.Serial;

public class ApplicationStartException extends CommonException {

    @Serial private static final long serialVersionUID = -8234910061543618240L;

    public ApplicationStartException(Class<?> aClass, Throwable cause) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, cause, "Failed to start an application %s", aClass.getSimpleName());
    }

}
