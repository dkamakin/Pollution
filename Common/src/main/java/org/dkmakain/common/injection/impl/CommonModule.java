package org.dkmakain.common.injection.impl;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import org.dkmakain.common.configuration.IConfigurationResolver;
import org.dkmakain.common.configuration.impl.ConfigurationResolver;

public class CommonModule extends AbstractModule {

    @Provides
    IConfigurationResolver configurationResolver() {
        return new ConfigurationResolver();
    }

}
