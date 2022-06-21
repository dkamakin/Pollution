package org.dkmakain.web.server;

import io.activej.inject.annotation.Inject;
import org.dkmakain.common.base.ConfigurationHolder;
import org.dkmakain.common.logger.Log;
import org.dkmakain.common.runner.IRunner;
import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.configuration.WebServerConfigurationSupplier;
import org.dkmakain.web.server.resolver.IServerResolver;

public class WebServer implements IRunner {

    private static final Log LOGGER = Log.create(WebServer.class);

    private volatile IInnerServer                                server;
    private final    IServerResolver                             resolver;
    private final    ConfigurationHolder<WebServerConfiguration> configurationHolder;

    @Inject
    public WebServer(final WebServerConfigurationSupplier configurationSupplier,
                     final IServerResolver resolver) {
        this.resolver            = resolver;
        this.configurationHolder = new ConfigurationHolder<>(configurationSupplier::get, this::updateConfig);
    }

    @Override
    public void run() {
        configurationHolder.checkConfig();

        LOGGER.information("Run Webserver, config: {}", configurationHolder.getConfig());

        server.run();
    }

    @Override
    public void stop() {
        if (server == null) {
            return;
        }

        LOGGER.information("Stop WebServer, config: {}", configurationHolder.getConfig());

        server.stop();
    }

    private void updateConfig(WebServerConfiguration newConfig) {
        stop();

        server = resolver.resolve(newConfig);
    }

}
