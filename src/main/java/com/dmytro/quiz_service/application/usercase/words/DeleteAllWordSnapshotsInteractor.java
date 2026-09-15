package com.dmytro.quiz_service.application.usercase.words;

import com.dmytro.quiz_service.domain.ports.in.DeleteAllWordSnapshotsUseCase;
import com.dmytro.quiz_service.domain.ports.out.WordSnapshotPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAllWordSnapshotsInteractor implements DeleteAllWordSnapshotsUseCase {
    private final WordSnapshotPort port;

    @Override
    public void deleteAllWordSnapshots(String ownerEmail) {
        port.deleteAllByOwnerEmail(ownerEmail);
    }
}
