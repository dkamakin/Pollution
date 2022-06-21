package org.dkmakain.common.multithreading;

import java.time.Duration;
import java.util.function.Supplier;
import org.dkmakain.common.event.IEvent;

public interface AfterRunEvent extends IEvent, Supplier<Duration> {

}
