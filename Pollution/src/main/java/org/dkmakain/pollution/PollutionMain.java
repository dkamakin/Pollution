package org.dkmakain.pollution;

import org.dkmakain.common.runner.impl.ApplicationRunner;
import org.dkmakain.pollution.injection.PollutionModule;

public class PollutionMain {

    public static void main(String[] args) {
        new ApplicationRunner(PollutionRunner.class,
                              new PollutionModule()).run();
    }

}
