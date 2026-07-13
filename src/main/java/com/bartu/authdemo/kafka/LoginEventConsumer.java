package com.bartu.authdemo.kafka;

import com.bartu.authdemo.LoginHistory;
import com.bartu.authdemo.LoginHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LoginEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LoginEventConsumer.class);

    private final LoginHistoryRepository loginHistoryRepository;

    public LoginEventConsumer(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

    @KafkaListener(topics = KafkaTopicConfig.LOGIN_EVENTS_TOPIC)
    public void consume(LoginEvent event) {

        logger.info(
                "Received LOGIN event: userId={}, email={}, successful={}, occurredAt={}",
                event.userId(),
                event.email(),
                event.successful(),
                event.occurredAt()
        );

        LoginHistory loginHistory = new LoginHistory();

        loginHistory.setUserId(event.userId());
        loginHistory.setEmail(event.email());
        loginHistory.setSuccessful(event.successful());
        loginHistory.setOccurredAt(event.occurredAt());

        loginHistoryRepository.save(loginHistory);
    }
}