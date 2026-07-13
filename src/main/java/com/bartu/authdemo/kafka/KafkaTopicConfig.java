package com.bartu.authdemo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String USER_REGISTERED_TOPIC = "user-registered";
    public static final String LOGIN_EVENTS_TOPIC = "login-events";

    @Bean
    public NewTopic userRegisteredTopic() {
        return TopicBuilder
                .name(USER_REGISTERED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic loginEventsTopic(){
        return TopicBuilder
                .name(LOGIN_EVENTS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

}