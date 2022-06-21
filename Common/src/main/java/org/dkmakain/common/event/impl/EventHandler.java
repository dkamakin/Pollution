package org.dkmakain.common.event.impl;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import org.dkmakain.common.event.EventSubscriber;
import org.dkmakain.common.event.IEvent;
import org.dkmakain.common.event.IEventHandler;

public class EventHandler<T extends IEvent<?>> implements IEventHandler<T> {

    private final Collection<EventSubscriber<T>> subscribers;

    public EventHandler() {
        this(Collections.emptyList());
    }

    public EventHandler(Collection<EventSubscriber<T>> subscribers) {
        this.subscribers = Sets.newConcurrentHashSet(subscribers);
    }

    @Override
    public void subscribe(EventSubscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(EventSubscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifyAll(T event) {
        iterateAndExecute(subscriber -> subscriber.process(event));
    }

    private void iterateAndExecute(Consumer<EventSubscriber<T>> action) {
        subscribers.forEach(action);
    }

}
