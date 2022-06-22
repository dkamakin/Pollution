package org.dkmakain.common.base;

import java.util.function.Supplier;

public abstract class ConfigurationSaver<T> {

    protected volatile T           currentConfiguration;
    private final      Supplier<T> configSupplier;

    protected ConfigurationSaver(Supplier<T> configSupplier) {
        this.configSupplier = configSupplier;
    }

    protected void checkConfig() {
        if (isNewConfigArrived()) {
            synchronized (this) {
                if (isNewConfigArrived()) {
                    updateConfig();
                }
            }
        }
    }

    public void updateConfig() {
        currentConfiguration = getConfig();

        initializeNewConfiguration(currentConfiguration);
    }

    protected T getConfig() {
        return configSupplier.get();
    }

    protected void initializeNewConfiguration(T newConfiguration) {

    }

    protected boolean isNewConfigArrived() {
        return currentConfiguration == null || !currentConfiguration.equals(getConfig());
    }

}
