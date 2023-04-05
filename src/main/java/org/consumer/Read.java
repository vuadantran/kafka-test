package org.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Read {
    public static void main(String[] args) {
        // Set up Kafka consumer properties
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "b-1.kzuatmsk.rd6fnd.c5.kafka.ap-southeast-1.amazonaws.com:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");

        // Create Kafka consumer instance
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        // Assign the partition to the consumer
        String topic = "test1";
//        int partition = 0; // partition number starts from 0
//        TopicPartition topicPartition = new TopicPartition(topic, partition);
//        consumer.assign(Collections.singletonList(topicPartition));

        consumer.subscribe(Collections.singleton(topic));
        // Start consuming messages
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Offset = %d - partition = %d, Key = %s, Value = %s%n", record.offset(), record.partition(), record.key(), record.value());
            }
        }

    }
}
