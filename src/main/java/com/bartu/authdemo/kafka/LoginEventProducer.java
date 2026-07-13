package com.bartu.authdemo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginEventProducer {

    private final KafkaTemplate<String, LoginEvent> kafkaTemplate;

    public LoginEventProducer(KafkaTemplate<String, LoginEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(LoginEvent event){
        kafkaTemplate.send(KafkaTopicConfig.LOGIN_EVENTS_TOPIC, event);
    }

}
