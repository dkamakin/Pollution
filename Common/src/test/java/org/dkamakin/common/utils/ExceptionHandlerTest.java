package org.dkamakin.common.utils;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.function.Consumer;
import org.dkmakain.common.interfaces.ThrowingOperation;
import org.dkmakain.common.utils.ExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExceptionHandlerTest {

    ThrowingOperation operation;
    Consumer          consumer;

    @BeforeEach
    public void setUp() {
        setUpConsumer();
        setUpOperation();
    }

    public void setUpOperation() {
        operation = mock(ThrowingOperation.class);
    }

    public void setUpConsumer() {
        consumer = mock(Consumer.class);
    }

    public void whenNeedToThrow(Exception exception) throws Throwable {
        doAnswer(invocation -> {
            throw exception;
        }).when(operation).perform();
    }

    @Test
    void performAndCatchAny_NoException_NoCallsToConsumer() throws Throwable {
        ExceptionHandler.performAndCatchAny(operation, consumer);

        verify(operation).perform();
        verifyNoInteractions(consumer);
    }

    @Test
    void performAndCatchAny_ExceptionWhenPerforming_CallConsumer() throws Throwable {
        Exception exception = new Exception();

        whenNeedToThrow(exception);

        ExceptionHandler.performAndCatchAny(operation, consumer);

        verify(operation).perform();
        verify(consumer).accept(exception);
    }

    @Test
    void performAndCatch_ThrowWrongException_NotConsumed() throws Throwable {
        RuntimeException exception = new RuntimeException();

        whenNeedToThrow(exception);

        ExceptionHandler.performAndCatch(operation, consumer, IllegalArgumentException.class);

        verify(operation).perform();
        verifyNoInteractions(consumer);
    }
}
