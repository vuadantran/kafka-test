package org.example;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(KafkaProducerConfig.class, KafkaTopicConfig.class, AB.class);
        applicationContext.getBean(AB.class).sendMessage("cc");
//        Thread.sleep(1000000L);
    }
}