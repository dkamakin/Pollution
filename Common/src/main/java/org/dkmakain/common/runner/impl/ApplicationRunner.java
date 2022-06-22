package org.dkmakain.common.runner.impl;

import io.activej.inject.module.AbstractModule;
import java.util.Collection;
import java.util.List;
import org.dkmakain.common.injection.impl.DependencyResolver;
import org.dkmakain.common.logger.Log;
import org.dkmakain.common.multithreading.impl.ConfigurableThread;
import org.dkmakain.common.runner.IApplicationRunner;
import org.dkmakain.common.runner.IRunner;

public class ApplicationRunner implements IApplicationRunner {

    private static final Log LOGGER = Log.create(ApplicationRunner.class);

    private       IRunner                    thread;
    private final Collection<AbstractModule> modules;
    private final Class<? extends IRunner>   starter;

    public ApplicationRunner(Class<? extends IRunner> starter, AbstractModule... modules) {
        setUpThread();

        this.modules = List.of(modules);
        this.starter = starter;
    }

    private void setUpThread() {
        thread = ConfigurableThread.builder()
                                   .setThreadName("Application-Runner")
                                   .setRunnable(this::startApplication)
                                   .setDaemon(false)
                                   .build();
    }

    private void startApplication() {
        LOGGER.information("Starting {} as an application", starter.getSimpleName());

        DependencyResolver.initialize(modules);
        DependencyResolver.resolve(starter).run();

        LOGGER.information("Application started successfully");
    }

    @Override
    public void run() {
        thread.run();
    }

    @Override
    public void stop() {
        DependencyResolver.resolve(starter).stop();

        thread.stop();
    }
}
