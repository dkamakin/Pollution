package org.dkmakain.common.event;

public interface EventSubscriber<T extends IEvent<?>> {

    void process(T event);

}
