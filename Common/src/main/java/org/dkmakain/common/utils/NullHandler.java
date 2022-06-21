package org.dkmakain.common.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.dkmakain.common.interfaces.ThrowingConsumer;

public class NullHandler {

    public static <T> T orValue(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static <T> T orExecute(T value, Supplier<T> supplier) {
        return value == null ? supplier.get() : value;
    }

    public static <T> void executeIfNotNull(T value, Consumer<T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    public static <T, E extends Exception> void executeThrowingIfNotNull(T value,
                                                                         ThrowingConsumer<T, E> action) throws E {
        if (value != null) {
            action.accept(value);
        }
    }

}
