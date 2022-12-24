package org.dkmakain.common.multithreading.impl;

import org.dkmakain.common.event.IEventSubscriber;
import org.dkmakain.common.multithreading.IAfterRunEvent;

public class VerifierSubscriber implements IEventSubscriber<IAfterRunEvent> {

    @Override
    public void process(IAfterRunEvent event) {
        if (event.getArguments().isIntervalEmpty()) {
            event.getArguments().notifier().stop();
        }
    }

}
