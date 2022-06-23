package org.dkmakain.common.multithreading.impl;

import java.time.Duration;
import java.util.Optional;

public record AfterRunArguments(Optional<Duration> interval, ConfigurableThread notifier) {

}
