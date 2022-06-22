package org.dkamakin.common.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.dkmakain.common.utils.Validator;
import org.junit.jupiter.api.Test;

class ValidatorTest {

    @Test
    void ifTrueThenThrow_True_ThrowException() {
        RuntimeException exception = new RuntimeException();
        assertThrows(RuntimeException.class, () -> Validator.ifTrue(true).thenThrow(exception));
    }

    @Test
    void ifTrueThenThrow_False_NoException() {
        assertDoesNotThrow(() -> Validator.ifTrue(false).thenThrow(new RuntimeException()));
    }

    @Test
    void ifFalseThenThrow_False_ThrowException() {
        RuntimeException exception = new RuntimeException();
        assertThrows(RuntimeException.class, () -> Validator.ifFalse(false).thenThrow(exception));
    }

    @Test
    void ifFalseThenThrow_True_NoException() {
        assertDoesNotThrow(() -> Validator.ifFalse(true).thenThrow(new RuntimeException()));
    }

    @Test
    void ifNullThenThrow_Null_ThrowException() {
        RuntimeException exception = new RuntimeException();
        assertThrows(RuntimeException.class, () -> Validator.ifNull(null).thenThrow(exception));
    }

    @Test
    void ifNullThenThrow_NotNull_NoException() {
        assertDoesNotThrow(() -> Validator.ifNull(new Object()).thenThrow(new RuntimeException()));
    }

}
