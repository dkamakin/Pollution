package org.dkmakain.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;

public class JsonUtils {

    private static final ObjectMapper MAPPER = initMapper();

    private static ObjectMapper initMapper() {
        var mapper = new ObjectMapper();

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

    private static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static JsonNode readTree(File file) throws IOException {
        return getMapper().readTree(file);
    }

    public static <T> String serialize(T value) throws JsonProcessingException {
        return getMapper().writeValueAsString(value);
    }

    public static <T> T deserialize(String json, Class<T> aClass) throws JsonProcessingException {
        return getMapper().readValue(json, aClass);
    }

    public static <T> T deserialize(JsonNode node, Class<T> type) throws JsonProcessingException {
        return getMapper().treeToValue(node, type);
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(T value) throws JsonProcessingException {
        return (T) deserialize(serialize(value), value.getClass());
    }

}
