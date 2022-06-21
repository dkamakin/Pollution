package org.dkmakain.common.multithreading.impl;

import java.time.Duration;
import java.time.Instant;
import org.dkmakain.common.event.EventSubscriber;
import org.dkmakain.common.exception.EventProcessingException;
import org.dkmakain.common.logger.Log;
import org.dkmakain.common.multithreading.AfterRunEvent;

public class WaiterSubscriber extends EventSubscriber<AfterRunEvent> {

    private static final Log LOGGER = Log.create(WaiterSubscriber.class);

    @Override
    public void process(AfterRunEvent event) {
        try {
            awaitNextExecutionTime(event.get());
        } catch (InterruptedException e) {
            throw new EventProcessingException("Interrupted while waiting for a next cycle");
        }
    }

    private void awaitNextExecutionTime(Duration timeout) throws InterruptedException {
        Instant deadline = Instant.now().plus(timeout);

        LOGGER.information("Waiting for {}", timeout);

        synchronized (this) {
            while (Instant.now().isBefore(deadline)) {
                wait(timeout.toMillis());
            }
        }

        LOGGER.information("Successfully waited");
    }

}
