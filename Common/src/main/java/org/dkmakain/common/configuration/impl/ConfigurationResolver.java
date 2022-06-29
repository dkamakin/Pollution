package org.dkmakain.common.configuration.impl;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Function;
import org.dkmakain.common.configuration.IConfigurationReader;
import org.dkmakain.common.configuration.IConfigurationResolver;

public class ConfigurationResolver implements IConfigurationResolver {

    private       Configuration              configuration;
    private final ConfigurationReaderFactory readerFactory;

    public ConfigurationResolver() {
        this.readerFactory = new ConfigurationReaderFactory();
    }

    @Override
    public <T> T get(Class<T> aClass) {
        return configuration.get(aClass);
    }

    @Override
    public void initialize(ConfigurationLocator locator) {
        configuration = readerFactory.create(locator).read();
    }

    private static class ConfigurationReaderFactory {

        private final Map<ConfigurationLocation, Function<ConfigurationPosition, IConfigurationReader>> factory;

        private ConfigurationReaderFactory() {
            this.factory = Maps.newHashMap();

            factory.put(ConfigurationLocation.LOCAL, LocalConfigurationReader::new);
        }

        public IConfigurationReader create(ConfigurationLocator locator) {
            return factory.get(locator.location()).apply(locator.position());
        }

    }
}
