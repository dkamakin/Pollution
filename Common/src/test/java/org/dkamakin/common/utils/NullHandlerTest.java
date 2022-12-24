package org.dkamakin.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.dkmakain.common.utils.NullHandler;
import org.junit.jupiter.api.Test;

class NullHandlerTest {

    public static class Data {

        public static final String VALUE   = "value";
        public static final String DEFAULT = "default";
    }

    @Test
    void orValue_NullValue_ReturnDefault() {
        String defaultValue = Data.DEFAULT;

        assertEquals(defaultValue, NullHandler.orValue(null, defaultValue));
    }

    @Test
    void orValue_NullEverything_ReturnNull() {
        String defaultValue = null;

        assertEquals(defaultValue, NullHandler.orValue(null, defaultValue));
    }

    @Test
    void orValue_NotNullValue_ReturnValue() {
        String value        = Data.VALUE;
        String defaultValue = Data.DEFAULT;

        assertEquals(value, NullHandler.orValue(value, defaultValue));
    }

    @Test
    void orExecute_NullValue_Execute() {
        String           defaultValue = Data.DEFAULT;
        Supplier<String> supplier     = () -> defaultValue;

        assertEquals(defaultValue, NullHandler.orExecute(null, supplier));
    }

    @Test
    void orExecute_NotNullValue_Value() {
        String           defaultValue = Data.DEFAULT;
        String           value        = Data.VALUE;
        Supplier<String> supplier     = () -> defaultValue;

        assertEquals(value, NullHandler.orExecute(value, supplier));
    }

    @Test
    void executeIfNotNull_Null_NoExecution() {
        Consumer action = mock(Consumer.class);

        NullHandler.notNull(null, action);

        verifyNoInteractions(action);
    }

    @Test
    void executeIfNotNull_NotNull_Execute() {
        Consumer action = mock(Consumer.class);
        String   value  = "value";

        NullHandler.notNull(value, action);

        verify(action).accept(value);
    }
}
