package org.dkmakain.web.exception;

import java.io.Serial;
import org.dkmakain.common.exception.CommonException;
import org.dkmakain.common.exception.ErrorReason;

public class UnsupportedWebServer extends CommonException {

    @Serial private static final long serialVersionUID = -8254265001407585523L;

    public UnsupportedWebServer(String message, Object... params) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, message, params);
    }
}
