package org.dkmakain.web.exception;

import java.io.Serial;
import org.dkmakain.common.exception.CommonException;
import org.dkmakain.common.exception.ErrorReason;

public class WebServerOperationException extends CommonException {

    @Serial private static final long serialVersionUID = -6793871775896737480L;

    public WebServerOperationException(String message, Object... params) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, message, params);
    }
}
