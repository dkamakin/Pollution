package org.dkmakain.web.injection;

import io.activej.inject.annotation.Provides;
import org.dkmakain.common.configuration.IConfigurationResolver;
import org.dkmakain.common.injection.impl.CommonModule;
import org.dkmakain.web.server.impl.WebServer;
import org.dkmakain.web.server.resolver.IServerResolver;
import org.dkmakain.web.server.resolver.impl.ServerResolver;

public class WebModule extends CommonModule {


    @Provides
    IServerResolver serverResolver() {
        return new ServerResolver();
    }

    @Provides
    WebServer webServer(IConfigurationResolver configurationResolver,
                        IServerResolver resolver) {
        return new WebServer(configurationResolver, resolver);
    }

}
