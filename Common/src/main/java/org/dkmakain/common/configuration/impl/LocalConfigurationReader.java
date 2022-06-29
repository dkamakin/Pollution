package org.dkmakain.common.configuration.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.nio.file.Path;
import org.dkmakain.common.configuration.IConfigurationReader;
import org.dkmakain.common.exception.ConfigurationResolvingException;
import org.dkmakain.common.json.JsonUtils;
import org.dkmakain.common.logger.Log;

public class LocalConfigurationReader implements IConfigurationReader {

    private static final Log LOGGER = Log.create(LocalConfigurationReader.class);

    private final ConfigurationPosition position;

    public LocalConfigurationReader(ConfigurationPosition position) {
        this.position = position;
    }

    @Override
    public Configuration read() {
        try {
            return process(position);
        } catch (Exception e) {
            LOGGER.exception(e.getMessage(), e);
            throw new ConfigurationResolvingException(e, "Failed to read configuration, position: %s", position);
        }
    }

    protected Configuration process(ConfigurationPosition position) throws IOException {
        var      result = new Configuration();
        JsonNode file   = JsonUtils.readTree(Path.of(position.path()).toFile());

        var iterator = file.fields();

        while (iterator.hasNext()) {
            var entry = iterator.next();

            safePut(entry.getKey(), entry.getValue(), result);
        }

        return result;
    }

    private ConfigurationValue getValue(JsonNode node, Class<?> aClass) throws JsonProcessingException {
        return new ConfigurationValue(JsonUtils.deserialize(node, aClass));
    }

    private void safePut(String className, JsonNode node, Configuration configuration) {
        try {
            Class<?> aClass = Class.forName(className);

            configuration.put(aClass, getValue(node, aClass));
        } catch (Exception e) {
            LOGGER.warning("Failed to read configuration, {}", e.getMessage());
        }
    }

}
