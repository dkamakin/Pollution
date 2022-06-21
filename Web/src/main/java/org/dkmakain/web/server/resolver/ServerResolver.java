package org.dkmakain.web.server.resolver;

import org.dkmakain.common.reflection.ReflectionUtils;
import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.exception.ServerResolvingException;
import org.dkmakain.web.server.IInnerServer;
import org.dkmakain.web.server.SupportedServers;

public class ServerResolver implements IServerResolver {

    @Override
    public IInnerServer resolve(WebServerConfiguration configuration) {
        try {
            return ReflectionUtils.instantiateFromConstructorNoPrimitives(SupportedServers.map(configuration.type),
                                                                          configuration);
        } catch (Exception e) {
            throw new ServerResolvingException(e.getMessage());
        }
    }

}
