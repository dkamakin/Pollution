package org.dkamakin.common.multithreading.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.function.Supplier;
import org.dkmakain.common.multithreading.impl.ConfigurableThread;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigurableThreadTest {

    ConfigurableThread target;
    Runnable           runnable;
    Supplier<Duration> intervalSupplier;

    @BeforeEach
    public void setUp() {
        setUpRunnable();
        setUpInterval();

        target = ConfigurableThread.builder()
                                   .setThreadName("Test-Thread")
                                   .setDaemon(true)
                                   .setRunnable(runnable)
                                   .setInterval(intervalSupplier)
                                   .build();
    }

    @AfterEach
    public void teardown() {
        target.stop();
    }

    public void setUpInterval() {
        intervalSupplier = mock(Supplier.class);
    }

    public void setUpRunnable() {
        runnable = mock(Runnable.class);
    }

    public <E extends Exception> void whenNeedToThrowException(Supplier<E> exceptionSupplier) {
        doAnswer(invocation -> {
            throw exceptionSupplier.get();
        }).when(runnable).run();
    }

    public void whenNeedToGetInterval(Duration interval) {
        when(intervalSupplier.get()).thenReturn(interval);
    }

    @Test
    void run_FirstRun_Running() {
        target.run();

        verify(runnable, timeout(Duration.ofMinutes(1).toMillis()).atLeast(1)).run();
    }

    @Test
    void run_ExceptionWhileRunning_NoException() {
        whenNeedToThrowException(Exception::new);

        target.run();

        assertTrue(target.isRunning());

        verify(runnable, timeout(Duration.ofMinutes(1).toMillis()).atLeast(1)).run();
    }

    @Test
    void run_WithInterval_KeepInterval() {
        whenNeedToGetInterval(Duration.ofSeconds(10));

        target.run();

        assertTrue(target.isRunning());

        verify(runnable, timeout(Duration.ofSeconds(25).toMillis()).times(2)).run();
    }

    @Test
    void isRunning_RunStop_NotRunning() {
        target.run();

        assertTrue(target.isRunning());

        target.stop();

        assertFalse(target.isRunning());
    }

    @Test
    void isRunning_NoActions_NotRunning() {
        assertFalse(target.isRunning());
    }

    @Test
    void isRunning_RunThread_Running() {
        target.run();

        assertTrue(target.isRunning());
    }

}
