package org.dkmakain.common.configuration;

import org.dkmakain.common.configuration.impl.ConfigurationLocator;

public interface IConfigurationResolver {

    <T> T get(Class<T> aClass);

    void initialize(ConfigurationLocator locator);

}
