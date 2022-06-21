package org.dkmakain.common.utils;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Validator {

    public static Examiner<Boolean> ifTrue(boolean result) {
        return new Examiner<>(tested -> tested, result);
    }

    public static Examiner<Boolean> ifFalse(boolean result) {
        return new Examiner<>(tested -> !tested, result);
    }

    public static <T> Examiner<T> ifNull(T value) {
        return new Examiner<>(Objects::isNull, value);
    }

    public static class Examiner<T> {

        Predicate<T> predicate;
        T            value;

        public Examiner(Predicate<T> predicate, T value) {
            this.predicate = predicate;
            this.value     = value;
        }

        public <E extends RuntimeException> T thenThrow(E exception) {
            if (Boolean.TRUE.equals(predicate.test(value))) {
                throw exception;
            }

            return value;
        }

        public void thenThrow(Supplier<? extends RuntimeException> exceptionSupplier) {
            thenThrow(exceptionSupplier.get());
        }

    }

}
