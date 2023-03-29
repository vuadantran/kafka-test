package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Consumer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(KafkaTopicConfig.class, KafkaProducerConfig.class,
                        KafkaConsumerConfig.class, KafkaProcessing.class, KafkaListener.class);
    }
}
