package org.dkamakin.common.configuration.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.dkmakain.common.configuration.impl.Configuration;
import org.dkmakain.common.configuration.impl.ConfigurationValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigurationTest {

    Configuration target;

    @BeforeEach
    public void setUp() {
        target = new Configuration();
    }

    @Test
    void size_NoConfiguration_Empty() {
        assertThat(target.getConfigurationMap()).isEmpty();
    }

    @Test
    void put_Configuration_Put() {
        Configuration instance = new Configuration();

        target.put(instance.getClass(), new ConfigurationValue(instance));

        assertThat(target.getConfigurationMap()).hasSize(1);
    }

    @Test
    void get_NotPresent_Null() {
        assertThat(target.get(Configuration.class)).isEmpty();
    }

    @Test
    void get_Present_CastAndReturn() {
        var expected = new Configuration();

        target.put(expected.getClass(), new ConfigurationValue(expected));

        var actual = target.get(expected.getClass());

        assertThat(actual).isPresent().hasValueSatisfying(result -> assertThat(result).isEqualTo(expected));
    }

    @Test
    void get_WrongClass_ClassCastException() {
        var instance = new Configuration();

        target.put(Integer.class, new ConfigurationValue(instance));

        assertThatThrownBy(() -> target.get(Integer.class)).isInstanceOf(ClassCastException.class);
    }

}
