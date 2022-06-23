package org.dkmakain.web.server.impl;

import io.activej.inject.annotation.Inject;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import org.dkmakain.common.base.ConfigurationSaver;
import org.dkmakain.common.interfaces.Operation;
import org.dkmakain.common.logger.Log;
import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.configuration.WebServerConfigurationSupplier;
import org.dkmakain.web.server.IInnerServer;
import org.dkmakain.web.server.IWebServer;
import org.dkmakain.web.server.resolver.IServerResolver;

public class WebServer extends ConfigurationSaver<WebServerConfiguration> implements IWebServer {

    private static final Log LOGGER = Log.create(WebServer.class);

    private       IInnerServer    server;
    private final IServerResolver resolver;

    @Inject
    public WebServer(final WebServerConfigurationSupplier configurationSupplier,
                     final IServerResolver resolver) {
        super(configurationSupplier::get);

        this.resolver = resolver;
    }

    @Override
    public synchronized void run() {
        checkConfig();

        checkedExecution(this::isRunnable, () -> server.run());
    }

    @Override
    public synchronized void stop() {
        checkedExecution(this::isStoppable, () -> server.stop());
    }

    @Override
    protected void initializeNewConfiguration(WebServerConfiguration newConfiguration) {
        stop();

        LOGGER.information("Resolving server, config {}", currentConfiguration);

        server = resolver.resolve(newConfiguration);

        LOGGER.information("Server resolved and initialized, instance: {}", server);
    }

    private void checkedExecution(BooleanSupplier condition, Operation operation) {
        if (condition.getAsBoolean()) {
            operation.perform();
        }
    }

    private boolean isStoppable() {
        return isExecutable(server, IInnerServer::isStoppable);
    }

    private boolean isRunnable() {
        return isExecutable(server, IInnerServer::isRunnable);
    }

    private boolean isExecutable(IInnerServer instance, Predicate<IInnerServer> function) {
        return instance != null && function.test(instance);
    }

}
