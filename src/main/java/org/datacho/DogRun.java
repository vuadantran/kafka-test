package org.datacho;

import com.fts.common.extensions.producer.KafkaProducer;
import com.fts.ioz.common.commonlibs.user.auth.common.utils.JsonUtils;
import datadog.trace.api.GlobalTracer;
import datadog.trace.api.Trace;
import datadog.trace.api.Tracer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.KafkaProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class DogRun {
    private static final Logger LOGGER = LoggerFactory.getLogger(DogRun.class);

    @Trace
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(KafkaProducerConfig.class, TriggerCreateUser.class);

        // Set up Kafka consumer properties
        Properties properties = new Properties();
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "b-1.kzuatmsk.rd6fnd.c5.kafka.ap-southeast-1.amazonaws.com:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "iozf-user-profile-workflow-engine");
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");

        // Create Kafka consumer instance
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        // Assign the partition to the consumer
        String topic = "KZ_IozfUpCreateNodeWalletTopic";

//        int partition = 0; // partition number starts from 0
//        TopicPartition topicPartition = new TopicPartition(topic, partition);
//        consumer.assign(Collections.singletonList(topicPartition));

        consumer.subscribe(Collections.singleton(topic));
        List<PostCreateUserMsg> messages = new ArrayList<>();
        // Start consuming messages
        int size = 1;
        while (size > 0) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            size = records.count();
            LOGGER.info("Record size " + size);
            for (ConsumerRecord<String, String> record : records) {
//                System.out.printf("Offset = %d - partition = %d, Key = %s, Value = %s%n", record.offset(), record.partition(), record.key(), record.value());
                messages.add(
                        JsonUtils.parseJsonToObject(record.value(), PostCreateUserMsg.class)
                );
            }
        }
//        messages = messages.subList(0, 10);
        TriggerCreateUser triggerCreateUser = new TriggerCreateUser();
        triggerCreateUser.send(messages, applicationContext.getBean(KafkaProducer.class), applicationContext.getBean(KafkaTemplate.class));
    }
}
