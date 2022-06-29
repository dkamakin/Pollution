package org.dkmakain.web.server.resolver.impl;

import org.dkmakain.common.reflection.ReflectionUtils;
import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.exception.ServerResolvingException;
import org.dkmakain.web.server.IInnerServer;
import org.dkmakain.web.server.impl.SupportedServers;
import org.dkmakain.web.server.resolver.IServerResolver;

public class ServerResolver implements IServerResolver {

    @Override
    public IInnerServer resolve(WebServerConfiguration configuration) {
        try {
            IInnerServer server = ReflectionUtils.instanceFromConstructorNoPrimitives(SupportedServers.map(
                                                                                          configuration.getType()),
                                                                                      configuration);

            server.initialize();

            return server;
        } catch (Exception e) {
            throw new ServerResolvingException(e.getMessage());
        }
    }

}
