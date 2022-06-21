package org.dkmakain.web.exception;

import java.io.Serial;
import org.dkmakain.common.exception.CommonException;
import org.dkmakain.common.exception.ErrorReason;

public class ServerResolvingException extends CommonException {

    @Serial private static final long serialVersionUID = -853932608544200850L;

    public ServerResolvingException(String message) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, "Failed to resolve server. Message: %s", message);
    }
}
