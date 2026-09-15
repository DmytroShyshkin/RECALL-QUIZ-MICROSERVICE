package com.dmytro.quiz_service.infrastructure.kafka.consumer.userDelete;

import com.dmytro.quiz_service.domain.ports.in.DeleteAllAnkiCardsUseCase;
import com.dmytro.quiz_service.domain.ports.in.DeleteAllQuizSessionsUseCase;
import com.dmytro.quiz_service.domain.ports.in.DeleteAllWordSnapshotsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeleteConsumer {

    private final DeleteAllAnkiCardsUseCase deleteAllAnkiCardsUseCase;
    private final DeleteAllQuizSessionsUseCase deleteAllQuizSessionsUseCase;
    private final DeleteAllWordSnapshotsUseCase deleteAllWordSnapshotsUseCase;

    @KafkaListener(
            topics="recall.user.delete"
            , groupId="delete-user"
            , containerFactory = "userDeleteContainerFactory"
    )
    public void handlerDeleteUser(UserDeleteEvent event){
        deleteAllAnkiCardsUseCase.deleteAllAnkiCards(event.userEmail());
        deleteAllQuizSessionsUseCase.deleteAllQuizCards(event.userEmail());
        deleteAllWordSnapshotsUseCase.deleteAllWordSnapshots(event.userEmail());
    }
}
