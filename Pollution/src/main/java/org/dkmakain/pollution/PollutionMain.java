package org.dkmakain.pollution;

import java.net.URISyntaxException;
import java.nio.file.Path;
import org.dkmakain.common.configuration.impl.ConfigurationLocation;
import org.dkmakain.common.configuration.impl.ConfigurationLocator;
import org.dkmakain.common.configuration.impl.ConfigurationPosition;
import org.dkmakain.common.runner.impl.ApplicationRunner;
import org.dkmakain.pollution.injection.PollutionModule;

public class PollutionMain {

    public static void main(String[] args) throws URISyntaxException {
        var configPath = Path.of(PollutionMain.class.getResource("/configuration.json").toURI());

        var runner = new ApplicationRunner(PollutionRunner.class,
                                           new ConfigurationLocator(new ConfigurationPosition(configPath.toString()),
                                                                    ConfigurationLocation.LOCAL),
                                           new PollutionModule());

        runner.run();

        Runtime.getRuntime().addShutdownHook(new Thread(runner::stop));
    }

}
