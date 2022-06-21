package org.dkmakain.common.utils;

import java.util.function.Consumer;
import org.dkmakain.common.interfaces.ThrowingOperation;

public class ExceptionHandler {

    public static <E extends Exception> void performAndCatch(ThrowingOperation<E> operation,
                                                             Consumer<E> exceptionConsumer,
                                                             Class<E> aClass) {
        try {
            operation.perform();
        } catch (Exception e) {
            if (e.getClass().equals(aClass)) {
                exceptionConsumer.accept(aClass.cast(e));
            }
        }
    }

    public static void performAndCatchAny(ThrowingOperation<Exception> operation,
                                          Consumer<Exception> exceptionConsumer) {
        performAndCatch(operation, exceptionConsumer, Exception.class);
    }

}
