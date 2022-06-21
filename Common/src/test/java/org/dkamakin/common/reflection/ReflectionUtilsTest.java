package org.dkamakin.common.reflection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.dkmakain.common.reflection.ReflectionUtils;
import org.junit.jupiter.api.Test;

class ReflectionUtilsTest {

    @Test
    void instantiateFromConstructorNoPrimitives_NoArgsConstructor_CorrectInstance() {
        assertDoesNotThrow(() -> ReflectionUtils.instantiateFromConstructorNoPrimitives(TestClass.class));
    }

    @Test
    void instantiateFromConstructorNoPrimitives_PrimitiveArgConstructor_NoSuchMethodException() {
        assertThrows(NoSuchMethodException.class,
                     () -> ReflectionUtils.instantiateFromConstructorNoPrimitives(TestClass.class,
                                                                                  1));
    }

    @Test
    void instantiateFromConstructorNoPrimitives_WrapperArgConstructor_CorrectInstance() {
        assertDoesNotThrow(() -> ReflectionUtils.instantiateFromConstructorNoPrimitives(TestClass.class,
                                                                                        1L));
    }

    @Test
    void instantiateFromConstructorNoPrimitives_ManyArgsConstructor_CorrectInstance() {
        assertDoesNotThrow(() -> ReflectionUtils.instantiateFromConstructorNoPrimitives(TestClass.class,
                                                                                        "test",
                                                                                        1,
                                                                                        new byte[1]));
    }

    public static class TestClass {

        public TestClass() {

        }

        public TestClass(int primitive) {

        }

        public TestClass(Long wrapper) {

        }

        public TestClass(String many, Integer arguments, byte[] here) {

        }

    }

}
