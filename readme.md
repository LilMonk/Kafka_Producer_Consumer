# Kafka Producer & Consumer For JSON

## Some links to get started:

<a href="https://www.cloudkarafka.com/blog/part1-kafka-for-beginners-what-is-apache-kafka.html">Kafka Overview</a>

<a href="https://kafka.apache.org/quickstart">Kafka Quickstart and Setup(without docker)</a>

<a href="https://www.youtube.com/watch?v=XFqm_ILuhs0&list=PLt1SIbA8guusxiHz9bveV-UHs_biWFegU">Video Playlist for Kafka Introduction Theory</a>

<a href="https://www.youtube.com/watch?v=5AENxG_Bvns">Video Tutorial for Producer & Consumer in Kafka</a>

## Kafka Transactions Theory

<a href="https://www.confluent.io/blog/transactions-apache-kafka/">Theory about transactions in Kafka(Must Read)</a>

<a href="https://www.baeldung.com/kafka-exactly-once">Code Illustrations for Transaction in Kafka</a>


## Setup

- ```docker-compose -f docker_compose_wurstmeister.yaml up -d``` to start kafka and zookeeper in docker.
- ```docker-compose -f docker_compose_wurstmeister.yaml down``` in same folder to stop kafka and zookeeper in docker.
- ```docker exec -it kafka /bin/sh``` to get inside the docker container.
- Inside docker container: 
    - ```cd \opt\kafka\bin``` to get inside dir with all the helper script.
    - ```kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 2 --partitions 3 --topic unique-topic-name``` to create a topic (required only once).
    - To start a bootstrap producer and consumer
        - ```kafka-console-producer.sh --broker-list localhost:9092 --topic unique-topic-name``` to start a producer.
        - ```kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic unique-topic-name``` to start a consumer.

## Java Code

### Description :

| File Name | Description | 
| :---: | :---: | 
| MessageSender.java | Main Producer | 
| MessageReciever.java | Main Consumer|
| Json.java | Handle json parsing|
| JsonSerializer.java | Custom serializer|
| JsonDeserializer.java | Custom deserializer|
| sample.json | Sample json data|

MessageSender and MessageReciever contains main method. 

Run ```MessageReciever.java (Consumer)``` first as it will loop for 100 iterations with 1s delay. 

Then run ```MessageSender.java (Producer)```. It will send the Json data in sample.json.

I have used vscode as the editor. Please check on other editor as well.