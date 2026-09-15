package com.dmytro.quiz_service.application.usercase.reconcile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dmytro.quiz_service.domain.ports.in.ReconcileOrphanedDataUseCase;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import com.dmytro.quiz_service.domain.ports.out.WordSnapshotPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReconcileOrphanedDataInteractor implements ReconcileOrphanedDataUseCase {

    private static final double MAX_ORPHANED_RATIO = 0.5;

    private final AnkiCardPort ankiCardPort;
    private final QuizRepositoryPort quizRepositoryPort;
    private final WordSnapshotPort wordSnapshotPort;

    @Override
    public void reconcile(Set<String> liveEmails) {
        if (liveEmails == null || liveEmails.isEmpty()) {
            log.warn("Reconciliation skipped: no live-email snapshot received yet (empty/null set).");
            return;
        }

        reconcileAnkiCards(liveEmails);
        reconcileQuizSessions(liveEmails);
        reconcileWordSnapshots(liveEmails);
    }

    private void reconcileAnkiCards(Set<String> liveEmails) {
        List<String> trackedEmails = ankiCardPort.findDistinctUserEmails();
        if (trackedEmails.isEmpty()) {
            return;
        }

        List<String> orphanedEmails = trackedEmails.stream()
                .filter(email -> !liveEmails.contains(email))
                .toList();

        if (!isSafeToDelete(orphanedEmails.size(), trackedEmails.size())) {
            log.error("Anki reconciliation aborted: {} of {} tracked emails look orphaned (> {}%) — " +
                            "treating this as a suspicious/incomplete snapshot rather than deleting.",
                    orphanedEmails.size(), trackedEmails.size(), (int) (MAX_ORPHANED_RATIO * 100));
            return;
        }

        orphanedEmails.forEach(ankiCardPort::deleteAllByUserEmail);

        if (!orphanedEmails.isEmpty()) {
            log.info("Reconciliation removed Anki cards for {} orphaned email(s): {}",
                    orphanedEmails.size(), orphanedEmails);
        }
    }

    private void reconcileQuizSessions(Set<String> liveEmails) {
        Set<UUID> liveUserIds = liveEmails.stream()
                .map(email -> UUID.nameUUIDFromBytes(email.getBytes()))
                .collect(Collectors.toSet());

        List<UUID> trackedUserIds = quizRepositoryPort.findDistinctUserIds();
        if (trackedUserIds.isEmpty()) {
            return;
        }

        List<UUID> orphanedUserIds = trackedUserIds.stream()
                .filter(userId -> !liveUserIds.contains(userId))
                .toList();

        if (!isSafeToDelete(orphanedUserIds.size(), trackedUserIds.size())) {
            log.error("Quiz session reconciliation aborted: {} of {} tracked userIds look orphaned (> {}%) — " +
                            "treating this as a suspicious/incomplete snapshot rather than deleting.",
                    orphanedUserIds.size(), trackedUserIds.size(), (int) (MAX_ORPHANED_RATIO * 100));
            return;
        }

        orphanedUserIds.forEach(quizRepositoryPort::deleteAllByUserId);

        if (!orphanedUserIds.isEmpty()) {
            log.info("Reconciliation removed quiz sessions for {} orphaned userId(s)", orphanedUserIds.size());
        }
    }

    private void reconcileWordSnapshots(Set<String> liveEmails) {
        List<String> trackedEmails = wordSnapshotPort.findDistinctOwnerEmails();
        if (trackedEmails.isEmpty()) {
            return;
        }

        List<String> orphanedEmails = trackedEmails.stream()
                .filter(email -> !liveEmails.contains(email))
                .toList();

        if (!isSafeToDelete(orphanedEmails.size(), trackedEmails.size())) {
            log.error("Word snapshot reconciliation aborted: {} of {} tracked emails look orphaned (> {}%) — " +
                            "treating this as a suspicious/incomplete snapshot rather than deleting.",
                    orphanedEmails.size(), trackedEmails.size(), (int) (MAX_ORPHANED_RATIO * 100));
            return;
        }

        orphanedEmails.forEach(wordSnapshotPort::deleteAllByOwnerEmail);

        if (!orphanedEmails.isEmpty()) {
            log.info("Reconciliation removed word snapshots for {} orphaned email(s): {}",
                    orphanedEmails.size(), orphanedEmails);
        }
    }

    private boolean isSafeToDelete(int orphanedCount, int totalCount) {
        return orphanedCount == 0 || (double) orphanedCount / totalCount <= MAX_ORPHANED_RATIO;
    }

}
