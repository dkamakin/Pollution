package org.dkmakain.common.event.impl;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import org.dkmakain.common.event.IEvent;
import org.dkmakain.common.event.IEventHandler;
import org.dkmakain.common.event.IEventSubscriber;

public class EventHandler<T extends IEvent<?>> implements IEventHandler<T> {

    private final Collection<IEventSubscriber<T>> subscribers;

    public EventHandler() {
        this(Collections.emptyList());
    }

    public EventHandler(Collection<IEventSubscriber<T>> subscribers) {
        this.subscribers = Sets.newHashSet(subscribers);
    }

    @Override
    public void subscribe(IEventSubscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(IEventSubscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifyAll(T event) {
        subscribers.forEach(subscriber -> subscriber.process(event));
    }

}
