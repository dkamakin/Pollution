package org.dkmakain.common.configuration.impl;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;

public class Configuration {

    private final Map<Class<?>, ConfigurationValue> configurationMap;

    public Configuration() {
        this.configurationMap = Maps.newConcurrentMap();
    }

    public <T> Optional<T> get(Class<T> aClass) {
        return value(aClass).map(ConfigurationValue::instance).map(aClass::cast);
    }

    public Map<Class<?>, ConfigurationValue> getConfigurationMap() {
        return configurationMap;
    }

    public void put(Class<?> key, ConfigurationValue value) {
        configurationMap.put(key, value);
    }

    private Optional<ConfigurationValue> value(Class<?> aClass) {
        return Optional.ofNullable(configurationMap.get(aClass));
    }

}
