package org.dkmakain.common.multithreading.impl;

import org.dkmakain.common.event.IEventSubscriber;
import org.dkmakain.common.multithreading.IAfterRunEvent;
import org.dkmakain.common.utils.Validator;

public class VerifierSubscriber implements IEventSubscriber<IAfterRunEvent> {

    @Override
    public void process(IAfterRunEvent event) {
        validateIfPeriodic(event.getArguments().interval().isPresent(), event.getArguments().notifier());
    }

    private void validateIfPeriodic(boolean isDurationSupplied, ConfigurableThread notifier) {
        Validator.ifFalse(isDurationSupplied).thenExecute(notifier::stop);
    }

}
