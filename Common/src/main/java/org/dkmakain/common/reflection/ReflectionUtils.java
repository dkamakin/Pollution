package org.dkmakain.common.reflection;

import com.google.common.collect.ImmutableMap;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ReflectionUtils {

    private static final Map<Class<?>, Class<?>> WRAPPERS_TO_PRIMITIVE = new ImmutableMap.Builder<Class<?>, Class<?>>()
        .put(Boolean.class, boolean.class)
        .put(Byte.class, byte.class)
        .put(Character.class, char.class)
        .put(Double.class, double.class)
        .put(Float.class, float.class)
        .put(Integer.class, int.class)
        .put(Long.class, long.class)
        .put(Short.class, short.class)
        .put(Void.class, void.class)
        .build();

    public static <T> T instanceFromConstructorNoPrimitives(Class<T> aClass, Object... parameters)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        return aClass.getConstructor(getClasses(parameters)).newInstance(parameters);
    }

    private static Class<?>[] getClasses(Object... parameters) {
        var result = new Class[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            result[i] = parameters[i].getClass();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> convertToPrimitive(Class<T> aClass) {
        Class<T> primitive = (Class<T>) WRAPPERS_TO_PRIMITIVE.get(aClass);
        return primitive == null ? aClass : primitive;
    }
}
