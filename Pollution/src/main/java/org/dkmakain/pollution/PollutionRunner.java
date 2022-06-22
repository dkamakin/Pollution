package org.dkmakain.pollution;

import io.activej.inject.annotation.Inject;
import org.dkmakain.common.runner.IRunner;
import org.dkmakain.web.server.impl.WebServer;

public class PollutionRunner implements IRunner {

    private final WebServer webServer;

    @Inject
    public PollutionRunner(final WebServer webServer) {
        this.webServer = webServer;
    }

    @Override
    public void run() {
        webServer.run();
    }

    @Override
    public void stop() {
        webServer.stop();
    }
}
