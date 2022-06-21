package org.dkamakin.common.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.time.Duration;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Supplier;
import org.dkmakain.common.base.ConfigurationHolder;
import org.dkmakain.common.json.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigurationHolderTest {

    private static class Data {

        public static final Integer OLD_VALUE = 1;
        public static final Integer NEW_VALUE = 2;
    }

    Supplier<Configuration>            configurationSupplier;
    Configuration                      configuration;
    ConfigurationHolder<Configuration> target;

    @BeforeEach
    public void setUp() {
        setUpConfiguration();

        configurationSupplier = spy(new ConfigurationSupplier());

        target = spy(new ConfigurationHolder<>(configurationSupplier));
    }

    public void setUpConfiguration() {
        configuration        = new Configuration();
        configuration.number = Data.OLD_VALUE;
    }

    @Test
    void checkConfig_FirstCall_UpdateConfigOnce() {
        target.checkConfig();

        Configuration currentConfig = target.getConfig();

        verify(configurationSupplier, times(2)).get();
        verify(target).updateConfig();

        assertEquals(configuration, currentConfig);
    }

    @Test
    void checkConfig_CallTwiceSameConfig_UpdateConfigOnce() {
        target.checkConfig();

        target.checkConfig();

        Configuration currentConfig = target.getConfig();

        verify(configurationSupplier, times(3)).get();
        verify(target).updateConfig();

        assertEquals(configuration, currentConfig);
    }

    @Test
    void checkConfig_CallThenUpdateConfigThenCallAgain_UpdateConfigTwice() {
        target.checkConfig();

        configuration.number = Data.NEW_VALUE;

        target.checkConfig();

        Configuration currentConfig = target.getConfig();

        verify(configurationSupplier, times(5)).get();
        verify(target, times(2)).updateConfig();

        assertEquals(configuration, currentConfig);
    }

    @Test
    void checkConfig_ConcurrentCheck_OnlyOneThreadUpdates() throws BrokenBarrierException, InterruptedException {
        int           countUsersPlusMain = 4;
        CyclicBarrier cyclicBarrier      = new CyclicBarrier(countUsersPlusMain);

        for (int i = 0; i < countUsersPlusMain - 1; i++) {
            new TestServiceUser<>(target, cyclicBarrier).start();
        }

        cyclicBarrier.await();

        verify(target, timeout(Duration.ofMinutes(1).toMillis())).updateConfig();
    }


    static class TestServiceUser<T> extends Thread {

        ConfigurationHolder<T> target;
        CyclicBarrier          cyclicBarrier;

        TestServiceUser(ConfigurationHolder<T> target, CyclicBarrier cyclicBarrier) {
            this.target        = target;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();

                target.checkConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            return JsonUtils.clone(ConfigurationHolderTest.this.configuration);
        }
    }
}
