package com.dmytro.quiz_service.adapters.in.rest.quiz;

import com.dmytro.quiz_service.adapters.in.rest.quiz.dto.AnswerQuizRequest;
import com.dmytro.quiz_service.adapters.in.rest.quiz.dto.QuizQuestionResponse;
import com.dmytro.quiz_service.adapters.in.rest.quiz.dto.QuizSessionResponse;
import com.dmytro.quiz_service.adapters.in.rest.quiz.dto.StartQuizRequest;
import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.domain.ports.in.AnswerQuizUseCase;
import com.dmytro.quiz_service.domain.ports.in.DeleteQuizSessionUseCase;
import com.dmytro.quiz_service.domain.ports.in.GetQuizResultUseCase;
import com.dmytro.quiz_service.domain.ports.in.StartQuizUseCase;
import com.dmytro.quiz_service.infrastructure.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final StartQuizUseCase startQuiz;
    private final AnswerQuizUseCase answerQuiz;
    private final GetQuizResultUseCase getQuizResult;
    private final DeleteQuizSessionUseCase deleteQuizSession;
    private final JwtUtil jwtUtil;

    @PostMapping("/start")
    public ResponseEntity<QuizSessionResponse> start(
            @RequestHeader("Authorization") String jwt,
            @RequestBody StartQuizRequest request
    ) {
        String email = jwtUtil.extractEmail(jwt);

        QuizSession session = startQuiz.startQuiz(
                UUID.nameUUIDFromBytes(email.getBytes()), // userId from email
                jwt,
                request.languages(),
                request.wordCount()
        );

        return ResponseEntity.ok(toResponse(session));
    }

    @PostMapping("/{sessionId}/answer")
    public ResponseEntity<QuizSessionResponse> answer(
            @PathVariable UUID sessionId,
            @RequestBody AnswerQuizRequest request
    ) {
        QuizSession session = answerQuiz.answerQuiz(sessionId, request.userAnswer());
        return ResponseEntity.ok(toResponse(session));
    }

    @GetMapping("/{sessionId}/result")
    public ResponseEntity<QuizSessionResponse> result(
            @PathVariable UUID sessionId
    ) {
        QuizSession session = getQuizResult.getQuizResult(sessionId);
        return ResponseEntity.ok(toResponse(session));
    }

    @DeleteMapping("/{sessionId}/deleteSession")
    public ResponseEntity<QuizSessionResponse> delete(
            @PathVariable UUID sessionId
    ) {
        deleteQuizSession.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    private QuizSessionResponse toResponse(QuizSession session) {
        QuizQuestionResponse currentQuestion = null;

        if (!session.isCompleted() && session.getQuestions() != null) {
            var q = session.getQuestions().get(session.getCurrentIndex());
            currentQuestion = new QuizQuestionResponse(
                    q.getWordId(),
                    q.getQuestion(),
                    q.getOptions(),
                    q.getLanguage()
            );
        }

        return new QuizSessionResponse(
                session.getSessionId(),
                session.getCurrentIndex(),
                session.getQuestions() != null ? session.getQuestions().size() : 0,
                session.getScore(),
                session.isCompleted(),
                currentQuestion
        );
    }
}
