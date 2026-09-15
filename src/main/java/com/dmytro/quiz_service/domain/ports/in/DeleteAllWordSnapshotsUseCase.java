package com.dmytro.quiz_service.domain.ports.in;

public interface DeleteAllWordSnapshotsUseCase {
    void deleteAllWordSnapshots(String ownerEmail);
}
