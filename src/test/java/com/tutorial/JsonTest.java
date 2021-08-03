package com.tutorial;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

public class JsonTest {


    private static String readJsonFromFile(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Test
    void testParse() {
        String fileName = "src/main/resources/sample.json";
        Json json = new Json();
        try {
            JsonNode json_file = json.parse(readJsonFromFile(fileName));
            System.out.println(json_file.get("quiz").get("sport"));
            assertNotEquals(json_file.asText(), null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
