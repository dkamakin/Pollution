package org.dkmakain.web.server;

import org.dkmakain.common.runner.IRunner;

public interface IInnerServer extends IRunner {

    boolean isStoppable();

    boolean isRunnable();

    void initialize();

}
