package org.dkamakin.web.server;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.dkmakain.common.configuration.IConfigurationResolver;
import org.dkmakain.web.configuration.JettyConfiguration;
import org.dkmakain.web.configuration.ServerType;
import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.server.IInnerServer;
import org.dkmakain.web.server.impl.WebServer;
import org.dkmakain.web.server.resolver.IServerResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WebServerTest {

    WebServer              target;
    IServerResolver        resolver;
    IInnerServer           server;
    WebServerConfiguration configuration;
    IConfigurationResolver configurationResolver;

    @BeforeEach
    public void setUp() {
        setUpResolver();
        setUpConfigurationResolver();

        target = spy(new WebServer(configurationResolver, resolver));
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
        configuration = new WebServerConfiguration(new JettyConfiguration(8080), ServerType.JETTY);
    }

    public void setUpConfigurationResolver() {
        setUpConfiguration();

        configurationResolver = mock(IConfigurationResolver.class);

        when(configurationResolver.get(WebServerConfiguration.class)).thenReturn(configuration);
    }

    public void whenNeedToAnswerIsRunnable(boolean result) {
        when(server.isRunnable()).thenReturn(result);
    }

    public void whenNeedToAnswerIsStoppable(boolean result) {
        when(server.isStoppable()).thenReturn(result);
    }

    @Test
    void run_FirstRun_ResolveAndRun() {
        whenNeedToAnswerIsRunnable(true);

        target.run();

        verify(resolver).resolve(configuration);
        verify(server).run();
    }

    @Test
    void run_NotRunnable_NoAction() {
        whenNeedToAnswerIsRunnable(false);

        target.run();

        verify(resolver).resolve(configuration);
        verify(server, never()).run();
    }

    @Test
    void run_SecondRun_ResolveOnceStartTwice() {
        whenNeedToAnswerIsRunnable(true);

        target.run();
        target.run();

        verify(resolver).resolve(configuration);
        verify(server, times(2)).run();
    }

    @Test
    void run_NewConfigurationArrived_StopAndResolve() {
        whenNeedToAnswerIsStoppable(true);
        whenNeedToAnswerIsRunnable(true);

        target.run();

        WebServerConfiguration newConfig = new WebServerConfiguration(null, ServerType.JETTY);

        when(configurationResolver.get(WebServerConfiguration.class)).thenReturn(newConfig);

        target.run();

        verify(resolver).resolve(newConfig);
        verify(resolver).resolve(configuration);
        verify(server).stop();
        verify(server, times(2)).run();
    }

    @Test
    void stop_NotStoppable_NoAction() {
        whenNeedToAnswerIsStoppable(false);

        target.stop();

        verifyNoInteractions(server);
    }

    @Test
    void stop_RunStop_Stopped() {
        whenNeedToAnswerIsStoppable(true);
        whenNeedToAnswerIsRunnable(true);

        target.run();
        target.stop();

        verify(server).stop();
    }

}
