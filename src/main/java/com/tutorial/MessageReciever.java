package com.tutorial;

import java.util.Collections;
import java.util.Properties;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class MessageReciever {
    static final String TOPIC = "tutorial";
    static final String GROUP = "tutorial_group";

    private Consumer<String, JsonNode> consumer = null;

    Properties props = new Properties();

    MessageReciever() {
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("kafka.topic.name", "tutorial");
        props.setProperty("group.id", GROUP);
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("isolation.level", "read_committed"); // This is to read only non-transactional data or data whose transactions are completed.
    }


    /**
     * Create only one consumer per instance.
     * @return consumer
     */
    private Consumer<String, JsonNode> getJsonConsumer() {
        if (this.consumer == null)
            this.consumer = new KafkaConsumer<String, JsonNode>(props, new StringDeserializer(),
                    new JsonDesrializer());
        return this.consumer;
    }

    /**
     * Recieve json data from kafka
     */
    public void recieveJson() {
        Consumer<String, JsonNode> consumer = getJsonConsumer();
        consumer.subscribe(Collections.singletonList(TOPIC));
        for (int i = 0; i < 100; i++) {
            ConsumerRecords<String, JsonNode> records = consumer.poll(1000L);
            System.out.println("Size: " + records.count());
            records.forEach(record -> {
                System.out.println(record.toString());
            });
        }
    }

    public static void main(String[] args) {
        MessageReciever messageReciever = new MessageReciever();
        messageReciever.recieveJson();
    }
}
