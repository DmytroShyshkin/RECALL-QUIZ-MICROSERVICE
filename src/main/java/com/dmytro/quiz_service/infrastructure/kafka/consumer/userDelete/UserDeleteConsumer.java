package com.dmytro.quiz_service.infrastructure.kafka.consumer.userDelete;

import com.dmytro.quiz_service.domain.ports.in.DeleteAllAnkiCardsUseCase;
import com.dmytro.quiz_service.domain.ports.in.DeleteAllQuizSessionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@RequiredArgsConstructor
public class UserDeleteConsumer {

    private final DeleteAllAnkiCardsUseCase deleteAllAnkiCardsUseCase;
    private final DeleteAllQuizSessionsUseCase deleteAllQuizSessionsUseCase;

    @KafkaListener(topics="recall.user.delete", groupId="delete-user")
    public void handlerDeleteUser(UserDeleteEvent event){
        deleteAllAnkiCardsUseCase.deleteAllAnkiCards(event.userEmail);
        deleteAllQuizSessionsUseCase.deleteAllQuizCards(event.userEmail);
    }
}
