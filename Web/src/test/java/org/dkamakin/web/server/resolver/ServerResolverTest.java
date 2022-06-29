package org.dkamakin.web.server.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.dkmakain.web.configuration.JettyConfiguration;
import org.dkmakain.web.configuration.ServerType;
import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.server.jetty.JettyServer;
import org.dkmakain.web.server.resolver.impl.ServerResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerResolverTest {

    WebServerConfiguration configuration;
    ServerResolver         target;

    @BeforeEach
    public void setUp() {
        setUpConfiguration();

        target = new ServerResolver();
    }

    public void setUpConfiguration() {
        configuration = new WebServerConfiguration(new JettyConfiguration(8080), ServerType.JETTY);
    }

    @Test
    void resolve_JettyConfiguration_CorrectInstance() {
        assertEquals(JettyServer.class, target.resolve(configuration).getClass());
    }

}
