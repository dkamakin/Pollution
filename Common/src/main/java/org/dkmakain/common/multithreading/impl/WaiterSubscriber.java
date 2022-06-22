package org.dkmakain.common.multithreading.impl;

import java.time.Duration;
import java.time.Instant;
import org.dkmakain.common.event.IEventSubscriber;
import org.dkmakain.common.exception.EventProcessingException;
import org.dkmakain.common.logger.Log;
import org.dkmakain.common.multithreading.IAfterRunEvent;
import org.dkmakain.common.utils.NullHandler;

public class WaiterSubscriber implements IEventSubscriber<IAfterRunEvent> {

    private static final Log LOGGER = Log.create(WaiterSubscriber.class);

    @Override
    public void process(IAfterRunEvent event) {
        try {
            NullHandler.executeThrowingOperationIfNotNull(event.getArguments().interval(),
                                                          this::awaitNextExecutionTime);
        } catch (InterruptedException e) { // NOSONAR rethrow
            LOGGER.exception("Failed to wait", e);
            throw new EventProcessingException("Exception while waiting for a next cycle");
        }
    }

    private void awaitNextExecutionTime(Duration timeout) throws InterruptedException {
        Instant now      = Instant.now();
        Instant deadline = now.plus(timeout);

        LOGGER.information("Waiting for {}", timeout);

        synchronized (this) {
            while (now.isBefore(deadline)) {
                long millisToDeadline = Duration.between(now, deadline).toMillis();

                if (millisToDeadline > 0) {
                    wait(millisToDeadline);
                }

                now = Instant.now();
            }
        }

        LOGGER.information("Successfully waited");
    }

}
