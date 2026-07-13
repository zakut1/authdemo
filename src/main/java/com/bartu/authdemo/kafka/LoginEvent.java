package com.bartu.authdemo.kafka;

import java.time.LocalDateTime;

public record LoginEvent(
        Long userId,
        String email,
        boolean successful,
        LocalDateTime occurredAt
) {
}