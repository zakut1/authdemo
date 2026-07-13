package com.bartu.authdemo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationProducer {

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public UserRegistrationProducer(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(UserRegisteredEvent event) {

        kafkaTemplate.send(KafkaTopicConfig.USER_REGISTERED_TOPIC, event);
    }
}