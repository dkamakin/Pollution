package org.dkmakain.common.multithreading.impl;

import java.time.Duration;
import java.time.Instant;
import org.dkmakain.common.event.IEventSubscriber;
import org.dkmakain.common.exception.EventProcessingException;
import org.dkmakain.common.logger.Log;
import org.dkmakain.common.multithreading.IAfterRunEvent;

public class WaiterSubscriber implements IEventSubscriber<IAfterRunEvent> {

    private static final Log LOGGER = Log.create(WaiterSubscriber.class);

    @Override
    public void process(IAfterRunEvent event) {
        event.getArguments().intervalOptional().ifPresent(this::awaitNextExecutionTime);
    }

    private void awaitNextExecutionTime(Duration timeout) {
        try {
            wait(timeout);
        } catch (InterruptedException e) {
            LOGGER.exception("Failed to wait", e);
            Thread.currentThread().interrupt();
            throw new EventProcessingException("Exception while waiting for a next cycle");
        }
    }

    private void wait(Duration timeout) throws InterruptedException {
        var now      = Instant.now();
        var deadline = now.plus(timeout);

        LOGGER.information("Waiting for {}", timeout);

        synchronized (this) {
            while (now.isBefore(deadline)) {
                var millisToDeadline = Duration.between(now, deadline).toMillis();

                if (millisToDeadline > 0) {
                    wait(millisToDeadline);
                }

                now = Instant.now();
            }
        }

        LOGGER.information("Successfully waited");
    }

}
