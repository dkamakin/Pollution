package org.dkmakain.common.runner.impl;

import io.activej.inject.module.AbstractModule;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.dkmakain.common.configuration.IConfigurationResolver;
import org.dkmakain.common.configuration.impl.ConfigurationLocator;
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
    private final ConfigurationLocator       configurationLocator;

    public ApplicationRunner(Class<? extends IRunner> starter, ConfigurationLocator configurationLocator,
                             AbstractModule... modules) {
        setUpThread();

        this.modules              = List.of(modules);
        this.starter              = starter;
        this.configurationLocator = configurationLocator;
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

        locator().ifPresent(this::initializeConfiguration);

        DependencyResolver.resolve(starter).run();

        LOGGER.information("Application started successfully");
    }

    private Optional<ConfigurationLocator> locator() {
        return Optional.ofNullable(configurationLocator);
    }

    private void initializeConfiguration(ConfigurationLocator locator) {
        DependencyResolver.resolve(IConfigurationResolver.class).initialize(locator);
    }
}
