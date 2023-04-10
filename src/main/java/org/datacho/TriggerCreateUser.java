package org.datacho;

import com.fts.common.extensions.producer.KafkaCallbackHandler;
import com.fts.common.extensions.producer.KafkaProducer;
import com.fts.common.extensions.producer.impl.SimpleKafkaCallbackHandler;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

public class TriggerCreateUser {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerCreateUser.class);

//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(
//                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "b-1.kzuatmsk.rd6fnd.c5.kafka.ap-southeast-1.amazonaws.com:9092");
//        configProps.put(
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class);
//        configProps.put(
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                JsonSerializer.class);
//        configProps.put(ProducerConfig.LINGER_MS_CONFIG, "10");
////        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, "1000000");
////        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5_000);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    public KafkaTemplate<String, NodeMessage> kafkaTemplate() {
//        KafkaTemplate tem = new KafkaTemplate<>(producerFactory());
//        tem.setDefaultTopic("KZ_IozfUpCreateNodeWalletTopic");
//        return tem;
//    }

//    @Autowired
//    KafkaTemplate<String, NodeMessage> producer;
//    @Autowired
//    KafkaProducer kafkaProducer;

    // Use `@Trace` annotation to trace custom methods
    public void send(List<PostCreateUserMsg> messages, KafkaProducer kafkaProducer, KafkaTemplate kafkaTemplate) {

        Tracer tracer = GlobalTracer.get();
        KafkaCallbackHandler kafkaCallbackHandler = new SimpleKafkaCallbackHandler(messages.size());
        try (Scope scope = tracer.activateSpan(tracer.buildSpan("createNode.span").start())) {
            for (PostCreateUserMsg message : messages) {
                // Use `GlobalTracer` to trace blocks of inline code
//                LOGGER.warn("Send message " + message.getUserId());
                kafkaProducer.sendMessageSync(message.getUserId().toString(), message, kafkaTemplate, kafkaCallbackHandler);
            }

        }
        kafkaCallbackHandler.await();

        // [...]
    }
}
