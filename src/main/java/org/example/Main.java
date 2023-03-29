package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.SerializationUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(KafkaProducerConfig.class, KafkaTopicConfig.class, KafkaProcessing.class);


        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

//        Scanner scanner = new Scanner(System.in);
//        while(scanner.hasNext()) {
//            String a = scanner.next();
//            long s = System.currentTimeMillis();
//            applicationContext.getBean(KafkaProcessing.class).sendSyncMessage(a);
//            long e = System.currentTimeMillis();
//            System.out.println("Send message " + a + " time " + (e - s) + " at " + s);
//        }

//        byte[] b = {77, 115, 103, 32, 48};
//        System.out.println(
//                new String(b)
//        );

        List<KafkaMessage> msgs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String a = "Text " + i;
            msgs.add(new KafkaMessage(a));
        }
        LOGGER.warn("Message byte length " +
                SerializationUtils.serialize(msgs).length
        );
        long s = System.currentTimeMillis();
        boolean result = false;
        try {
            result = applicationContext.getBean(KafkaProcessing.class).sendSyncMessageV2(msgs);

        } catch (Exception e) {
            LOGGER.error("Got error {}", e.getMessage(), e);
        }
        long e = System.currentTimeMillis();
        LOGGER.warn("Send message result = " + result  + " - time " + (e - s) + " - start at " + format.format(new Date(s)));
    }
}