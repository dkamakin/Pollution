package org.dkmakain.common.multithreading.impl;

import org.dkmakain.common.event.IEventSubscriber;
import org.dkmakain.common.multithreading.IAfterRunEvent;
import org.dkmakain.common.utils.Validator;

public class VerifierSubscriber implements IEventSubscriber<IAfterRunEvent> {

    @Override
    public void process(IAfterRunEvent event) {
        validatePeriodic(event);
    }

    private void validatePeriodic(IAfterRunEvent event) {
        Validator.ifNull(event.getArguments().interval()).thenExecute(() -> event.getArguments().notifier().stop());
    }
}
