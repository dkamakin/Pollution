package org.dkamakin.common.event;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.dkmakain.common.event.IEvent;
import org.dkmakain.common.event.IEventSubscriber;
import org.dkmakain.common.event.impl.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventHandlerTest {

    EventHandler<TestEvent> target;
    TestIEventSubscriber    testSubscriber;
    TestIEventSubscriber    anotherSubscriber;
    TestEvent               testEvent;

    @BeforeEach
    public void setUp() {
        setUpTestEvent();
        setUpTestEventSubscribers();

        target = new EventHandler<>();
    }

    public void setUpTestEvent() {
        testEvent = spy(new TestEvent());
    }

    public void setUpTestEventSubscribers() {
        testSubscriber    = spy(new TestIEventSubscriber());
        anotherSubscriber = spy(new TestIEventSubscriber());
    }

    @Test
    void notifyAll_TwoSubscribers_Notified() {
        target.subscribe(testSubscriber);
        target.subscribe(anotherSubscriber);

        target.notifyAll(testEvent);

        verify(testSubscriber).process(testEvent);
        verify(anotherSubscriber).process(testEvent);
    }

    @Test
    void notifyAll_SubscribeUnsubscribe_NotNotified() {
        target.subscribe(testSubscriber);
        target.unsubscribe(testSubscriber);

        target.notifyAll(testEvent);

        verify(testSubscriber, never()).process(testEvent);
    }

    private static class TestEvent implements IEvent<Object> {

        @Override
        public Object getArguments() {
            return null;
        }
    }

    private static class TestIEventSubscriber implements IEventSubscriber<TestEvent> {

        @Override
        public void process(TestEvent event) {
            System.out.println("Wow nice notification bro");
        }

    }

}
