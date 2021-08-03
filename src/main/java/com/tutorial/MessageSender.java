package com.tutorial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class MessageSender {
    Properties props = new Properties();
    private KafkaProducer<String, JsonNode> producer = null;

    MessageSender() {
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("kafka.topic.name", "tutorial");
    }

    /**
     * Read json file.
     * @param file
     * @return json data in string format.
     * @throws Exception
     */
    private static String readJsonFromFile(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    /**
     * Create only one producer per instance.
     * @return producer
     */
    private Producer<String, JsonNode> getJsonProducer() {
        if (this.producer == null)
            this.producer = new KafkaProducer<String, JsonNode>(props, new StringSerializer(),
                    new JsonSerializer());

        return this.producer;
    }

    /**
     * Send Json data in transaction mode.
     * @param record
     */
    private void sendJsonSecurly(ProducerRecord<String, JsonNode> record) {
        props.setProperty("enable.idempotence", "true");
        props.setProperty("transactional.id", "prod-1");
        Producer<String, JsonNode> producer = getJsonProducer();
        producer.initTransactions();
        try {
            producer.beginTransaction();
            producer.send(record);
            producer.commitTransaction();
        } catch (ProducerFencedException e) {
            producer.close();
        } catch (KafkaException e) {
            producer.abortTransaction();
        }
    }

    /**
     * Send Json data casually.
     * @param record
     */
    private void sendJsonInsecurely(ProducerRecord<String, JsonNode> record) {
        Producer<String, JsonNode> producer = getJsonProducer();
        producer.send(record);
        producer.close();
    }

    /**
     * Send Json data from file to kafka.
     * @param fileName
     */
    public void sendJson(String fileName) {
        Json json = new Json();
        try {

            JsonNode json_file = json.parse(readJsonFromFile(fileName));
            ProducerRecord<String, JsonNode> record = new ProducerRecord<String, JsonNode>(
                    props.getProperty("kafka.topic.name"), json_file);
            // System.out.println(json_file.get("quiz"));

            // Uncomment any one of the below two.
            sendJsonSecurly(record);
            // sendJsonInsecurely(record);

        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }

    }

    /**
     * This is to test the producer. Sends Date info every 1s.
     * @throws InterruptedException
     */
    private void sendDataTest() throws InterruptedException {
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("kafka.topic.name", "tutorial");
        // props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props,
                new StringSerializer(), new ByteArraySerializer());

        for (int i = 0; i < 1000; i++) {
            String payload_string = i + " Message from java code " + new Date();
            byte[] payload = payload_string.getBytes();
            System.out.println(payload_string);

            ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>(
                    props.getProperty("kafka.topic.name"), payload);
            producer.send(record);
            Thread.sleep(1000);
        }

        producer.close();
    }



    public static void main(String[] args) {
        MessageSender msgSender = new MessageSender();

        String fileName = "src/main/resources/sample.json";
        msgSender.sendJson(fileName);

    }
}
