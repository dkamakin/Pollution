package org.dkmakain.pollution;

import org.dkmakain.common.runner.IApplicationRunner;
import org.dkmakain.common.runner.impl.ApplicationRunner;
import org.dkmakain.pollution.injection.PollutionModule;

public class PollutionMain {

    public static void main(String[] args) {
        IApplicationRunner runner = new ApplicationRunner(PollutionRunner.class,
                                                          new PollutionModule());

        runner.run();

        Runtime.getRuntime().addShutdownHook(new Thread(runner::stop));
    }

}
