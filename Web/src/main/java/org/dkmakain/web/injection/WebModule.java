package org.dkmakain.web.injection;

import io.activej.inject.annotation.Provides;
import org.dkmakain.common.injection.impl.CommonModule;
import org.dkmakain.web.configuration.WebServerConfigurationSupplier;
import org.dkmakain.web.server.impl.WebServer;
import org.dkmakain.web.server.resolver.IServerResolver;
import org.dkmakain.web.server.resolver.impl.ServerResolver;

public class WebModule extends CommonModule {

    @Provides
    WebServerConfigurationSupplier webServerConfiguration() {
        return new WebServerConfigurationSupplier();
    }

    @Provides
    IServerResolver serverResolver() {
        return new ServerResolver();
    }

    @Provides
    WebServer webServer(WebServerConfigurationSupplier webServerConfigurationSupplier,
                        IServerResolver resolver) {
        return new WebServer(webServerConfigurationSupplier, resolver);
    }

}
