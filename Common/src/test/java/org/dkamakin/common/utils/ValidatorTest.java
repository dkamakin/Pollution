package org.dkamakin.common.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.dkmakain.common.exception.NullArgumentException;
import org.dkmakain.common.utils.Validator;
import org.junit.jupiter.api.Test;

class ValidatorTest {

    @Test
    void ifTrueThenThrow_True_ThrowException() {
        NullArgumentException exception = new NullArgumentException("");
        assertThrows(NullArgumentException.class, () -> Validator.ifTrue(true).thenThrow(exception));
    }

    @Test
    void ifTrueThenThrow_False_NoException() {
        assertDoesNotThrow(() -> Validator.ifTrue(false).thenThrow(new NullArgumentException("")));
    }

    @Test
    void ifFalseThenThrow_False_ThrowException() {
        NullArgumentException exception = new NullArgumentException("");
        assertThrows(NullArgumentException.class, () -> Validator.ifFalse(false).thenThrow(exception));
    }

    @Test
    void ifFalseThenThrow_True_NoException() {
        assertDoesNotThrow(() -> Validator.ifFalse(true).thenThrow(new NullArgumentException("")));
    }

    @Test
    void ifNullThenThrow_Null_ThrowException() {
        NullArgumentException exception = new NullArgumentException("");
        assertThrows(NullArgumentException.class, () -> Validator.ifNull(null).thenThrow(exception));
    }

    @Test
    void ifNullThenThrow_NotNull_NoException() {
        assertDoesNotThrow(() -> Validator.ifNull(new Object()).thenThrow(new NullArgumentException("")));
    }

}
