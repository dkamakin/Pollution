package org.dkmakain.common.event;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

public class EventHandler<T extends IEvent> {

    private final Collection<EventSubscriber<T>> subscribers;

    public EventHandler() {
        this(Collections.emptyList());
    }

    public EventHandler(Collection<EventSubscriber<T>> subscribers) {
        this.subscribers = Sets.newConcurrentHashSet(subscribers);
    }

    public void subscribe(EventSubscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(EventSubscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifyAll(T event) {
        iterateAndExecute(subscriber -> subscriber.process(event));
    }

    private void iterateAndExecute(Consumer<EventSubscriber<T>> action) {
        subscribers.forEach(action);
    }

}
