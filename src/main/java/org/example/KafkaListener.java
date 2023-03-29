package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.Acknowledgment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@EnableKafka
@Configuration
public class KafkaListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListener.class);
    @org.springframework.kafka.annotation.KafkaListener(topics = "test1", groupId = "test1", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(KafkaMessage message, Acknowledgment acknowledgment) {

        LOGGER.warn("Received Message: " + message);
        acknowledgment.acknowledge();
    }
}
