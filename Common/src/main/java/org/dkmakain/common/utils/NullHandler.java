package org.dkmakain.common.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class NullHandler {

    public static <T> T orValue(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static <T> T orExecute(T value, Supplier<T> supplier) {
        return value == null ? supplier.get() : value;
    }

    public static <T> void notNull(T value, Consumer<T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

}
