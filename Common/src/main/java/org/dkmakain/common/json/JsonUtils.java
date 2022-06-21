package org.dkmakain.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.dkmakain.common.exception.JsonProcessingException;

public class JsonUtils {

    public static class Constant {

        public static final int MAX_STRING_LENGTH = 50;
    }

    private static final ObjectMapper MAPPER = initMapper();

    private static ObjectMapper initMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

    private static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static <T> String serialize(T value) {
        try {
            return getMapper().writeValueAsString(value);
        } catch (Exception e) {
            throw new JsonProcessingException(e, "Failed to serialize object: %S", value);
        }
    }

    public static <T> T deserialize(String json, Class<T> aClass) {
        try {
            return getMapper().readValue(json, aClass);
        } catch (Exception e) {
            throw new JsonProcessingException(e, "Failed to deserialize json", normalizeString(json));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(T value) {
        try {
            return (T) deserialize(serialize(value), value.getClass());
        } catch (Exception e) {
            throw new JsonProcessingException(e, "Failed to clone object: %S", value);
        }
    }

    private static String normalizeString(String string) {
        return string.substring(0, getNormalizedLength(string));
    }

    private static int getNormalizedLength(String string) {
        int stringLength = string.length();
        return Math.min(stringLength, Constant.MAX_STRING_LENGTH);
    }
}
