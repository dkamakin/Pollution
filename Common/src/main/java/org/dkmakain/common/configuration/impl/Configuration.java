package org.dkmakain.common.configuration.impl;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Function;

public class Configuration {

    private final Map<Class<?>, ConfigurationValue> configurationMap;

    public Configuration() {
        this.configurationMap = Maps.newConcurrentMap();
    }

    public <T> T get(Class<T> aClass) {
        return executeOrNull(aClass::cast, configurationMap.get(aClass));
    }

    public Map<Class<?>, ConfigurationValue> getConfigurationMap() {
        return configurationMap;
    }

    public void put(Class<?> key, ConfigurationValue value) {
        configurationMap.put(key, value);
    }

    private <T> T executeOrNull(Function<Object, T> function, ConfigurationValue value) {
        if (value == null) {
            return null;
        }

        return function.apply(value.instance());
    }

}
