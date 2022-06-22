package org.dkmakain.common.event;

public interface IEventSubscriber<T extends IEvent<?>> {

    void process(T event);

}
