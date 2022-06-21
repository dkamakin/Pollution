package org.dkmakain.common.exception;

import java.io.Serial;

public class EventProcessingException extends CommonException {

    @Serial private static final long serialVersionUID = 6790396252388665158L;

    public EventProcessingException(String message) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, message);
    }
}
