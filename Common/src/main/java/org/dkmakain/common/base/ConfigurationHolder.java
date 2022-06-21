package org.dkmakain.common.base;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigurationHolder<T> {

    private volatile T           currentConfiguration;
    private final    Supplier<T> configSupplier;
    private final    Consumer<T> additionalUpdateAction;

    public ConfigurationHolder(Supplier<T> configSupplier) {
        this(configSupplier, null);
    }

    public ConfigurationHolder(Supplier<T> configSupplier, Consumer<T> additionalUpdateAction) {
        this.configSupplier         = configSupplier;
        this.additionalUpdateAction = additionalUpdateAction;
    }

    public void checkConfig() {
        if (!isNewConfigArrived()) {
            return;
        }

        synchronized (this) {
            if (isNewConfigArrived()) {
                updateConfig();
            }
        }
    }

    public T updateConfig() {
        currentConfiguration = getConfig();

        if (additionalUpdateAction != null) {
            additionalUpdateAction.accept(currentConfiguration);
        }

        return currentConfiguration;
    }

    public T getConfig() {
        return configSupplier.get();
    }

    private boolean isNewConfigArrived() {
        return currentConfiguration == null || !currentConfiguration.equals(getConfig());
    }

}
