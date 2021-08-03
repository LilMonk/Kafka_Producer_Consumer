package com.tutorial;

import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class JsonDesrializer implements Deserializer<JsonNode> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> config, boolean isKey) {
        //Nothing to Configure
    }

    @Override
    public JsonNode deserialize(String topic, byte[] data) {
        if (data == null)
            return null;
        try {
            return objectMapper.readTree(data);
        } catch (IOException e) {
            throw new SerializationException("Error De-serializing JSON message", e);
        }
    }

    @Override
    public void close() {

    }
}
