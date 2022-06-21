package org.dkmakain.web.configuration;

/*
 * TODO: implement configuration service
 */

public class WebServerConfigurationSupplier {

    public WebServerConfiguration get() {
        var result = new WebServerConfiguration();

        result.type  = ServerType.JETTY;
        result.jetty = configureJetty();

        return result;
    }

    private JettyConfiguration configureJetty() {
        var result = new JettyConfiguration();

        result.port = 8080;

        return result;
    }

}
