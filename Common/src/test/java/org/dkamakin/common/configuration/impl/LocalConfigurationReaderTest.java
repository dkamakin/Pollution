package org.dkamakin.common.configuration.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import org.dkmakain.common.configuration.impl.Configuration;
import org.dkmakain.common.configuration.impl.ConfigurationPosition;
import org.dkmakain.common.configuration.impl.LocalConfigurationReader;
import org.junit.jupiter.api.Test;

class LocalConfigurationReaderTest {

    private static final class Data {

        public static final String BROKEN_CONFIGURATION_PATH = "/broken_configuration.json";
        public static final String CONFIGURATION_PATH        = "/configuration.json";
    }

    private ConfigurationPosition createPosition(String stringPath) throws URISyntaxException {
        var url  = LocalConfigurationReaderTest.class.getResource(stringPath);
        var path = Paths.get(url.toURI());

        return new ConfigurationPosition(path.toString());
    }

    private LocalConfigurationReader createTarget(String path) throws URISyntaxException {
        return new LocalConfigurationReader(createPosition(path));
    }

    @Test
    void read_CorrectConfiguration_ProvideInstance() throws URISyntaxException {
        LocalConfigurationReader target        = createTarget(Data.CONFIGURATION_PATH);
        Configuration            configuration = target.read();

        var result = configuration.get(TestConfiguration.class);

        assertEquals(10, result.getNumber());
        assertEquals("name", result.getString());
    }

    @Test
    void read_BrokenConfiguration_SkipAndDoNothing() throws URISyntaxException {
        LocalConfigurationReader target        = createTarget(Data.BROKEN_CONFIGURATION_PATH);
        Configuration            configuration = target.read();

        var result = configuration.get(TestConfiguration.class);

        assertEquals(10, result.getNumber());
        assertEquals("name", result.getString());
        assertEquals(1, configuration.getConfigurationMap().size());
    }
}
