package org.dkmakain.web.server;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.dkmakain.common.utils.Validator;
import org.dkmakain.web.configuration.ServerType;
import org.dkmakain.web.exception.UnsupportedWebServer;
import org.dkmakain.web.server.jetty.JettyServer;

public class SupportedServers {

    private static final Map<ServerType, Class<? extends IInnerServer>> INNER_SERVER_MAP =
        new ImmutableMap.Builder<ServerType, Class<? extends IInnerServer>>()
            .put(ServerType.JETTY, JettyServer.class)
            .build();

    public static Class<? extends IInnerServer> map(ServerType type) {
        return Validator.ifNull(INNER_SERVER_MAP.get(type))
                        .thenThrow(new UnsupportedWebServer("Server %s not supported", type));
    }

}
