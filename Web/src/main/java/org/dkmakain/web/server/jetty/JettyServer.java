package org.dkmakain.web.server.jetty;

import com.google.common.base.MoreObjects;
import org.dkmakain.common.logger.Log;
import org.dkmakain.common.utils.ExceptionHandler;
import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.exception.WebServerOperationException;
import org.dkmakain.web.server.IInnerServer;
import org.eclipse.jetty.server.Server;

public class JettyServer implements IInnerServer {

    private static final Log LOGGER = Log.create(JettyServer.class);

    private final WebServerConfiguration configuration;
    private       Server                 server;

    public JettyServer(WebServerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void initialize() {
        LOGGER.information("Initializing Jetty, config: {}", configuration);

        server = new Server();

        LOGGER.information("Jetty server initialized");
    }

    @Override
    public void run() {
        initialize();

        ExceptionHandler.performAndCatchAny(server::start, this::exceptionHandler);
    }

    @Override
    public void stop() {
        ExceptionHandler.performAndCatchAny(server::stop, this::exceptionHandler);
    }

    @Override
    public boolean isStoppable() {
        return server != null && (server.isRunning() || server.isStarted());
    }

    @Override
    public boolean isRunnable() {
        return server != null && server.isStopped();
    }

    private <E extends Exception> void exceptionHandler(E exception) {
        LOGGER.exception("Exception caught", exception);

        throw new WebServerOperationException("Failed to perform an operation with Jetty server: %s",
                                              exception.getMessage());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("configuration", configuration)
                          .add("server", server)
                          .toString();
    }
}
