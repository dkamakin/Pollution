package org.dkmakain.web.server.resolver;

import org.dkmakain.web.configuration.WebServerConfiguration;
import org.dkmakain.web.server.IInnerServer;

public interface IServerResolver {

    IInnerServer resolve(WebServerConfiguration configuration);

}
