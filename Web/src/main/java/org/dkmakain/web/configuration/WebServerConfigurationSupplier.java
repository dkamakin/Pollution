package org.dkmakain.web.configuration;

/*
 * TODO: implement configuration service
 */

public class WebServerConfigurationSupplier {

    private static WebServerConfiguration configuration = initialize();

    public WebServerConfiguration get() {
        return configuration;
    }

    private static WebServerConfiguration initialize() {
        var result = new WebServerConfiguration();

        result.type  = ServerType.JETTY;
        result.jetty = configureJetty();

        return result;
    }

    private static JettyConfiguration configureJetty() {
        var result = new JettyConfiguration();

        result.port = 8080;

        return result;
    }

}
