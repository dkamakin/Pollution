package org.dkmakain.common.event;

public interface IEventHandler<T extends IEvent<?>> {

    void subscribe(EventSubscriber<T> subscriber);

    void unsubscribe(EventSubscriber<T> subscriber);

    void notifyAll(T event);

}
