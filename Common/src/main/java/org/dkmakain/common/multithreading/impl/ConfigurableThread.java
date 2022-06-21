package org.dkmakain.common.multithreading.impl;


import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import org.dkmakain.common.event.impl.EventHandler;
import org.dkmakain.common.logger.Log;
import org.dkmakain.common.multithreading.AfterRunEvent;
import org.dkmakain.common.multithreading.IConfigurableThread;
import org.dkmakain.common.utils.ExceptionHandler;
import org.dkmakain.common.utils.NullHandler;

public class ConfigurableThread implements IConfigurableThread {

    private static final Log LOGGER = Log.create(ConfigurableThread.class);

    private       Thread                      thread;
    private final AtomicBoolean               running;
    private final ThreadConfig                config;
    private       EventHandler<AfterRunEvent> afterRunHandler;

    private ConfigurableThread(ThreadConfig config) {
        this.running = new AtomicBoolean(false);
        this.config  = config;

        setUpThread(config);
        setUpHandlers();
    }

    @Override
    public void run() {
        if (isRunning()) {
            return;
        }

        synchronized (this) {
            if (!isRunning()) {
                thread.start();

                running.set(true);

                LOGGER.information("Running thread, config: {}", config);
            }
        }
    }

    @Override
    public void stop() {
        if (running.getAndSet(false)) {
            LOGGER.information("Stopping thread: {}", config.getName());
        }
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }

    private void execute() {
        while (isRunning()) {

            LOGGER.trace("Running task");

            ExceptionHandler.performAndCatchAny(this::innerExecution, this::exceptionHandler);
        }

        LOGGER.information("Thread stopped");
    }

    private void innerExecution() {
        config.getRunnable().run();

        afterRunHandler.notifyAll(() -> new AfterRunArguments(getInterval()));
    }

    private Duration getInterval() {
        return config.getInterval() == null ? null : config.getInterval().get();
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
