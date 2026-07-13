package com.bartu.authdemo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationConsumer.class);

    @KafkaListener(topics = KafkaTopicConfig.USER_REGISTERED_TOPIC)
    public void consume(UserRegisteredEvent event) {

        logger.info(
                "Received USER_REGISTERED event: userId={}, email={}, eventType={}",
                event.userId(),
                event.email(),
                event.eventType()
        );
    }
}