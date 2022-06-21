package org.dkmakain.web.server.jetty;

import org.dkmakain.common.logger.Log;
import org.dkmakain.common.utils.ExceptionHandler;
import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.exception.WebServerOperationException;
import org.dkmakain.web.server.IInnerServer;
import org.eclipse.jetty.server.Server;

public class JettyServer implements IInnerServer {

    private static final Log LOGGER = Log.create(JettyServer.class);

    private final    WebServerConfiguration configuration;
    private volatile Server                 server;

    public JettyServer(WebServerConfiguration configuration) {
        this.configuration = configuration;
    }

    private void initialize() {
        LOGGER.information("Initializing Jetty, config: {}", configuration);

        if (server == null) {
            server = new Server();
        } else {
            stop();
        }

        LOGGER.information("Jetty server initialized");
    }

    @Override
    public void run() {
        if (!isStartable()) {
            return;
        }

        synchronized (this) {
            initialize();

            LOGGER.information("Starting Jetty");

            ExceptionHandler.performAndCatchAny(server::start, this::exceptionHandler);
        }
    }

    @Override
    public void stop() {
        if (!isStoppable()) {
            return;
        }

        synchronized (this) {
            LOGGER.information("Stopping server");

            ExceptionHandler.performAndCatchAny(server::stop, this::exceptionHandler);
        }
    }

    @Override
    public boolean isStoppable() {
        return server != null && (server.isRunning() || server.isStarted());
    }

    @Override
    public boolean isStartable() {
        return server == null || server.isStopped();
    }

    private <E extends Exception> void exceptionHandler(E exception) {
        LOGGER.exception("Exception caught", exception);

        throw new WebServerOperationException("Failed to perform an operation with Jetty server, message: %s",
                                              exception.getMessage());
    }
}
