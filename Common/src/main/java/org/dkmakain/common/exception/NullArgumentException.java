package org.dkmakain.common.exception;

import java.io.Serial;

public class NullArgumentException extends CommonException {

    @Serial private static final long serialVersionUID = 2910243132246039048L;

    public NullArgumentException(String argument) {
        super(ErrorReason.INTERNAL_SERVER_ERROR, "Argument %s can not be null", argument);
    }

}
