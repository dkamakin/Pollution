package org.dkmakain.common.multithreading.impl;


import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import org.dkmakain.common.event.IEventHandler;
import org.dkmakain.common.event.impl.EventHandler;
import org.dkmakain.common.logger.Log;
import org.dkmakain.common.multithreading.IAfterRunEvent;
import org.dkmakain.common.multithreading.IConfigurableThread;
import org.dkmakain.common.utils.ExceptionHandler;
import org.dkmakain.common.utils.NullHandler;

public class ConfigurableThread implements IConfigurableThread {

    private static final Log LOGGER = Log.create(ConfigurableThread.class);

    private       Thread                        thread;
    private final AtomicBoolean                 running;
    private final ThreadConfig                  config;
    private       IEventHandler<IAfterRunEvent> afterRunHandler;

    private ConfigurableThread(ThreadConfig config) {
        this.running = new AtomicBoolean(false);
        this.config  = config;

        setUpThread(config);
        setUpHandlers();
    }

    @Override
    public synchronized void run() {
        if (running.compareAndSet(false, true)) {
            LOGGER.information("Starting thread, config: {}", config);

            thread.start();
        }
    }

    @Override
    public void stop() {
        if (running.compareAndSet(true, false)) {
            LOGGER.information("Stopping thread: {}", config.getName());
        }
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }

    private void execute() {
        LOGGER.information("Start thread execution");

        ExceptionHandler.performAndCatchAny(this::executeCycle, this::exceptionHandler);

        LOGGER.information("Thread stopped");
    }

    private void executeCycle() {
        LOGGER.information("Thread started");

        while (isRunning()) {

            config.getRunnable().run();

            afterRunHandler.notifyAll(() -> new AfterRunArguments(getInterval(), this));
        }
    }

    private Optional<Duration> getInterval() {
        Duration value = config.getInterval() == null ? null : config.getInterval().get();
        return Optional.ofNullable(value);
    }

    public static ConfigurableThreadBuilder builder() {
        return new ConfigurableThreadBuilder();
    }

    private void setUpThread(ThreadConfig config) {
        thread = new Thread(this::execute);

        NullHandler.executeIfNotNull(config.getName(), name -> thread.setName(name));
        thread.setDaemon(NullHandler.orValue(config.isDaemon(), true));
    }

    private void setUpHandlers() {
        afterRunHandler = new EventHandler<>();

        afterRunHandler.subscribe(new WaiterSubscriber());
        afterRunHandler.subscribe(new VerifierSubscriber());
    }

    private void exceptionHandler(Exception exception) {
        LOGGER.exception("Exception caught", exception);
    }

    public static class ConfigurableThreadBuilder {

        private final ThreadConfig config;

        private ConfigurableThreadBuilder() {
            this.config = new ThreadConfig();
        }

        public ConfigurableThreadBuilder setInterval(Supplier<Duration> interval) {
            config.setInterval(interval);
            return this;
        }

        public ConfigurableThreadBuilder setRunnable(Runnable runnable) {
            config.setRunnable(runnable);
            return this;
        }

        public ConfigurableThreadBuilder setThreadName(String name) {
            config.setName(name);
            return this;
        }

        public ConfigurableThreadBuilder setDaemon(Boolean isDaemon) {
            config.setDaemon(isDaemon);
            return this;
        }

        public ConfigurableThread build() {
            return new ConfigurableThread(config);
        }
    }

}
