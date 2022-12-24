package org.dkmakain.common.multithreading.impl;

import java.time.Duration;
import java.util.Optional;

public record AfterRunArguments(Duration interval, ConfigurableThread notifier) {

    public Optional<Duration> intervalOptional() {
        return Optional.ofNullable(interval);
    }

    public boolean isIntervalEmpty() {
        return intervalOptional().isEmpty();
    }

}
