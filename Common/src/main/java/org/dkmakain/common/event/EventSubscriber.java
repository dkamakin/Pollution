package org.dkmakain.common.event;

public abstract class EventSubscriber<T extends IEvent> {

    public abstract void process(T event);

}
