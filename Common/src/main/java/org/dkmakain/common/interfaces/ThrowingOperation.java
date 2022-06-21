package org.dkmakain.common.interfaces;

@FunctionalInterface
public interface ThrowingOperation<E extends Throwable> {

    void perform() throws E;

}
