package org.example;

import com.fts.common.configuration.common.response.TopicConfig;
import com.fts.common.extensions.annotation.EnableKafkaTracing;
import com.fts.common.extensions.consumer.KafkaConsumerConfigInit;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaTracing
@PropertySource("classpath:application.properties")
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, KafkaMessage> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "test1");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
//        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 120000);
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
//        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 100000);
//        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 10_000);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> kafkaListenerContainerFactory() {

//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setUrl(bootstrapAddress);
        topicConfig.setTopic("test1");
        topicConfig.setGroupId("test1");
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> fac2 = KafkaConsumerConfigInit.getCommonKafkaListenerContainerFactory(topicConfig, KafkaMessage.class);

        return fac2;
    }

}
