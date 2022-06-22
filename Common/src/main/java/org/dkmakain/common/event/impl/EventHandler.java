package org.dkmakain.common.event.impl;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import org.dkmakain.common.event.IEventSubscriber;
import org.dkmakain.common.event.IEvent;
import org.dkmakain.common.event.IEventHandler;

public class EventHandler<T extends IEvent<?>> implements IEventHandler<T> {

    private final Collection<IEventSubscriber<T>> subscribers;

    public EventHandler() {
        this(Collections.emptyList());
    }

    public EventHandler(Collection<IEventSubscriber<T>> subscribers) {
        this.subscribers = Sets.newConcurrentHashSet(subscribers);
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
        iterateAndExecute(subscriber -> subscriber.process(event));
    }

    private void iterateAndExecute(Consumer<IEventSubscriber<T>> action) {
        subscribers.forEach(action);
    }

}
