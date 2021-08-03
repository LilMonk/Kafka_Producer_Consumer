package com.tutorial;

import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    private static ObjectMapper objectMapper = getObjectMapper();

    /**
     * Create an object mapper.
     * @return ObjectMapper
     */
    private static ObjectMapper getObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        return defaultObjectMapper;
    }

    /**
     * Parse the json data from string.
     * @param str - json data
     * @return JsonNode
     * @throws IOException
     */
    public JsonNode parse(String str) throws IOException {
        return objectMapper.readTree(str);
    }
}
