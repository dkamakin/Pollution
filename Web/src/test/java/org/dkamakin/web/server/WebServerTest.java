package org.dkamakin.web.server;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.dkmakain.web.configuration.ServerType;
import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.configuration.WebServerConfigurationSupplier;
import org.dkmakain.web.server.IInnerServer;
import org.dkmakain.web.server.WebServer;
import org.dkmakain.web.server.resolver.IServerResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebServerTest {

    WebServer                      target;
    IServerResolver                resolver;
    WebServerConfigurationSupplier configurationSupplier;
    IInnerServer                   server;
    WebServerConfiguration         configuration;

    @BeforeEach
    public void setUp() {
        setUpResolver();
        setUpConfigurationSupplier();

        target = new WebServer(configurationSupplier, resolver);
    }

    public void setUpServer() {
        server = mock(IInnerServer.class);
    }

    public void setUpResolver() {
        setUpServer();

        resolver = mock(IServerResolver.class);

        when(resolver.resolve(any())).thenReturn(server);
    }

    public void setUpConfiguration() {
        configuration = new WebServerConfiguration();
    }

    public void setUpConfigurationSupplier() {
        setUpConfiguration();

        configurationSupplier = mock(WebServerConfigurationSupplier.class);

        when(configurationSupplier.get()).thenReturn(configuration);
    }

    @Test
    void run_FirstRun_ResolveAndRun() {
        target.run();

        verify(resolver).resolve(configuration);
        verify(server).run();
    }

    @Test
    void run_SecondRun_ResolveOnceStartTwice() {
        target.run();
        target.run();

        verify(resolver).resolve(configuration);
        verify(server, times(2)).run();
    }

    @Test
    void run_NewConfigurationArrived_StopAndResolve() {
        target.run();

        WebServerConfiguration newConfig = new WebServerConfiguration();

        newConfig.type = ServerType.JETTY;

        when(configurationSupplier.get()).thenReturn(newConfig);

        target.run();

        verify(resolver).resolve(newConfig);
        verify(resolver).resolve(configuration);
        verify(server).stop();
        verify(server, times(2)).run();
    }

}
