package org.dkmakain.common.exception;

import com.google.common.base.MoreObjects;
import java.io.Serial;

public class CommonException extends RuntimeException {

    @Serial private static final long serialVersionUID = -3235800248254383388L;

    private final ErrorReason reason;

    public CommonException(ErrorReason reason, Throwable cause, String message, Object... params) {
        super(message.formatted(params), cause);

        this.reason = reason;
    }

    public CommonException(ErrorReason reason, String message, Object... params) {
        super(message.formatted(params));

        this.reason = reason;
    }

    public ErrorReason getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("reason", reason)
                          .toString();
    }
}
