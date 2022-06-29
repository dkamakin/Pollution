package org.dkamakin.common.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.time.Duration;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Supplier;
import org.dkmakain.common.base.ConfigurationSaver;
import org.dkmakain.common.json.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigurationSaverTest {

    private static class Data {

        public static final Integer OLD_VALUE = 1;
        public static final Integer NEW_VALUE = 2;
    }

    Supplier<Configuration> configurationSupplier;
    Configuration           configuration;
    ServiceTest             target;

    @BeforeEach
    public void setUp() {
        setUpConfiguration();

        configurationSupplier = spy(new ConfigurationSupplier());

        target = spy(new ServiceTest(configurationSupplier));
    }

    public void setUpConfiguration() {
        configuration        = new Configuration();
        configuration.number = Data.OLD_VALUE;
    }

    @Test
    void checkConfig_FirstCall_UpdateConfigOnce() {
        Configuration currentConfig = target.someFunction();

        verify(configurationSupplier).get();
        verify(target).updateConfig();

        assertEquals(configuration, currentConfig);
    }

    @Test
    void checkConfig_CallTwiceSameConfig_UpdateConfigOnce() {
        target.someFunction();
        Configuration currentConfig = target.someFunction();

        verify(configurationSupplier, times(2)).get();
        verify(target).updateConfig();

        assertEquals(configuration, currentConfig);
    }

    @Test
    void checkConfig_CallTwiceUpdateConfig_UpdateConfigTwice() {
        target.someFunction();

        configuration.number = Data.NEW_VALUE;

        Configuration currentConfig = target.someFunction();

        verify(configurationSupplier, times(4)).get();
        verify(target, times(2)).updateConfig();

        assertEquals(configuration, currentConfig);
    }

    @Test
    void checkConfig_ConcurrentCheck_OnlyOneThreadUpdates() throws BrokenBarrierException, InterruptedException {
        int           countUsersPlusMain = 4;
        CyclicBarrier cyclicBarrier      = new CyclicBarrier(countUsersPlusMain);

        for (int i = 0; i < countUsersPlusMain - 1; i++) {
            new TestServiceUser(target, cyclicBarrier).start();
        }

        cyclicBarrier.await();

        verify(target, timeout(Duration.ofMinutes(1).toMillis())).updateConfig();
    }


    static class TestServiceUser extends Thread {

        ServiceTest   target;
        CyclicBarrier cyclicBarrier;

        TestServiceUser(ServiceTest target, CyclicBarrier cyclicBarrier) {
            this.target        = target;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();

                target.someFunction();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static class ServiceTest extends ConfigurationSaver<Configuration> {

        protected ServiceTest(Supplier<Configuration> configSupplier) {
            super(configSupplier);
        }

        public Configuration someFunction() {
            checkConfig();

            someLogic();

            return currentConfiguration;
        }

        public void someLogic() {
            System.out.println("Some job done..");
        }

    }

    private static class Configuration {

        public Integer number;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Configuration that = (Configuration) o;
            return Objects.equal(number, that.number);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(number);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                              .add("number", number)
                              .toString();
        }
    }

    private class ConfigurationSupplier implements Supplier<Configuration> {

        @Override
        public Configuration get() {
            try {
                return JsonUtils.clone(ConfigurationSaverTest.this.configuration);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
