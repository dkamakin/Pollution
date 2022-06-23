package org.dkmakain.common.utils;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.dkmakain.common.interfaces.Operation;

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

        private void executeTrue(Operation operation) {
            if (Boolean.TRUE.equals(predicate.test(value))) {
                operation.perform();
            }
        }

        public void thenExecute(Operation operation) {
            executeTrue(operation);
        }

        public <E extends RuntimeException> T thenThrow(E exception) {
            executeTrue(() -> thrower(exception));

            return value;
        }

        public T thenThrow(Supplier<? extends RuntimeException> exceptionSupplier) {
            return thenThrow(exceptionSupplier.get());
        }

        private <E extends RuntimeException> void thrower(E exception) {
            throw exception;
        }

    }

}
