package com.dmytro.quiz_service.adapters.in.rest.anki;

import com.dmytro.quiz_service.adapters.in.rest.anki.dto.AnkiCardResponse;
import com.dmytro.quiz_service.adapters.in.rest.anki.dto.InitiateAnkiRequest;
import com.dmytro.quiz_service.adapters.in.rest.anki.dto.ReviewAnkiRequest;
import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.in.InitiateAnkiCard;
import com.dmytro.quiz_service.domain.ports.in.NextAnkiCard;
import com.dmytro.quiz_service.domain.ports.in.ReviewAnkiUseCase;
import com.dmytro.quiz_service.infrastructure.config.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/anki")
@AllArgsConstructor
public class AnkiController {

    private final InitiateAnkiCard initiateAnkiCard;
    private final NextAnkiCard nextAnkiCard;
    private final ReviewAnkiUseCase reviewAnkiUseCase;
    private final JwtUtil jwtUtil;

    @PostMapping("/initiate")
    public ResponseEntity<List<AnkiCardResponse>> initiate(
            @RequestHeader("Authorization") String jwt,
            @RequestBody InitiateAnkiRequest request
    ) {
        List<AnkiCard> cards = initiateAnkiCard.initiateAnkiCard(
                jwt,
                request.sourceLanguage(),
                request.targetLanguage()
        );

        return ResponseEntity.ok(cards.stream()
                .map(this::toResponse)
                .toList());
    }

    // next card for review
    @GetMapping("/next")
    public ResponseEntity<AnkiCardResponse> next(
            @RequestHeader("Authorization") String jwt
    ) {
        String email = jwtUtil.extractEmail(jwt);
        AnkiCard card = nextAnkiCard.nextAnkiCard(email);
        return ResponseEntity.ok(toResponse(card));
    }

    // rate this card (1–4)
    @PostMapping("/{cardId}/review")
    public ResponseEntity<AnkiCardResponse> review(
            @PathVariable UUID cardId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody ReviewAnkiRequest request
    ) {
        String email = jwtUtil.extractEmail(jwt);
        AnkiCard card = reviewAnkiUseCase.review(cardId, request.rating(), email);
        return ResponseEntity.ok(toResponse(card));
    }

    private AnkiCardResponse toResponse(AnkiCard card) {
        return new AnkiCardResponse(
                card.getId(),
                card.getWordId(),
                card.getWord(),
                card.getTranslations(),
                card.getState(),
                card.getRetrievability(),
                card.getNextReviewAt()
        );
    }
}
