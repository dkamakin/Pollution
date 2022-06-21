package org.dkmakain.common.interfaces;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

    void accept(T value) throws E;

}
