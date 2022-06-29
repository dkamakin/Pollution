package org.dkamakin.common.configuration.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals(0, target.getConfigurationMap().size());
    }

    @Test
    void put_Configuration_Put() {
        Configuration instance = new Configuration();

        target.put(instance.getClass(), new ConfigurationValue(instance));

        assertEquals(1, target.getConfigurationMap().size());
    }

    @Test
    void get_NotPresent_Null() {
        assertNull(target.get(Configuration.class));
    }

    @Test
    void get_Present_CastAndReturn() {
        Configuration instance = new Configuration();

        target.put(instance.getClass(), new ConfigurationValue(instance));

        Configuration result = target.get(instance.getClass());

        assertSame(instance, result);
    }

    @Test
    void get_WrongClass_ClassCastException() {
        Configuration instance = new Configuration();

        target.put(Integer.class, new ConfigurationValue(instance));

        assertThrows(ClassCastException.class, () -> target.get(Integer.class));
    }

}
