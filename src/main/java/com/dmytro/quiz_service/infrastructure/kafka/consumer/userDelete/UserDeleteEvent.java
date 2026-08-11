package com.dmytro.quiz_service.infrastructure.kafka.consumer.userDelete;

public record UserDeleteEvent(
        String userEmail
) {
}
