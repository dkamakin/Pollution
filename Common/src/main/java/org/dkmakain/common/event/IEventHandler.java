package org.dkmakain.common.event;

public interface IEventHandler<T extends IEvent<?>> {

    void subscribe(IEventSubscriber<T> subscriber);

    void unsubscribe(IEventSubscriber<T> subscriber);

    void notifyAll(T event);

}
