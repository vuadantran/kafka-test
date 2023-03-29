package org.example;

import com.fts.common.extensions.producer.KafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class KafkaProcessing {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProcessing.class);

    @Value("${spring.kafka.bootstrap}")
    private String bootstrapAddress;

    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Bean
    public KafkaProducer kafkaProducer() {
        return new KafkaProducer(null);
    }

    @Autowired
    KafkaProducer kafkaProducer;

    public void sendSyncMessage(String msg) {

        kafkaProducer.sendMessageSync(kafkaTemplate, new KafkaMessage(msg));

    }

    public void sendASyncMessage(String msg) {
        kafkaProducer.sendMessageAsync(kafkaTemplate, new KafkaMessage(msg));
    }

    public boolean sendSyncMessageV2(List<KafkaMessage> msgs) {
        return kafkaProducer.sendMessageFullSync(kafkaTemplate, msgs);
    }

    public void send(String msg) {
    }
}
