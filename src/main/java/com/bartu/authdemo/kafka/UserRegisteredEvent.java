package com.bartu.authdemo.kafka;

public record UserRegisteredEvent(Long userId, String email, String eventType) {

}