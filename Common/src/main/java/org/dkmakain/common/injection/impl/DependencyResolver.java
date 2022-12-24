package org.dkmakain.common.injection.impl;

import io.activej.inject.Injector;
import io.activej.inject.module.AbstractModule;
import java.util.Collection;

public class DependencyResolver {

    private static Injector injector;

    public static void initialize(Collection<AbstractModule> modules) {
        injector = Injector.of(modules.toArray(new AbstractModule[0]));
    }

    public static <T> T resolve(Class<T> aClass) {
        return injector.getInstance(aClass);
    }

}
