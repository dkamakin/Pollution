package org.dkmakain.pollution.injection;

import io.activej.inject.annotation.Provides;
import org.dkmakain.pollution.PollutionRunner;
import org.dkmakain.web.injection.WebModule;
import org.dkmakain.web.server.impl.WebServer;

public class PollutionModule extends WebModule {

    @Provides
    PollutionRunner pollutionRunner(WebServer webServer) {
        return new PollutionRunner(webServer);
    }

}
