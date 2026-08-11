package com.dmytro.quiz_service.application.usercase.quiz;

import com.dmytro.quiz_service.domain.exception.QuizAlreadyCompletedException;
import com.dmytro.quiz_service.domain.exception.QuizSessionNotFoundException;
import com.dmytro.quiz_service.domain.model.QuizQuestion;
import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.domain.ports.in.AnswerQuizUseCase;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import com.dmytro.quiz_service.domain.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AnswerQuizInteractor implements AnswerQuizUseCase {

    private final QuizRepositoryPort quizRepository;
    private final QuizService quizService;

    @Override
    public QuizSession answerQuiz(UUID sessionId, String userAnswer) {

        QuizSession session = quizRepository.findSessionById(sessionId)
                .orElseThrow(() -> new QuizSessionNotFoundException(sessionId.toString()));

        if (session.isCompleted()) {
            throw new QuizAlreadyCompletedException(sessionId.toString());
        }

        QuizQuestion current = session.getQuestions().get(session.getCurrentIndex());
        current.setUserAnswer(userAnswer);

        boolean correct = quizService.checkAnswer(current, userAnswer);
        current.setAnsweredCorrectly(correct);

        if (correct) {
            session.setScore(session.getScore() + 1);
            session.getCorrectAnswer().add(current);
        } else {
            session.getWrongAnswer().add(current);
        }

        int nextIndex = session.getCurrentIndex() + 1;
        if (nextIndex >= session.getQuestions().size()) {
            session.setCompleted(true);
            session.setFinishedAt(LocalDateTime.now());
        } else {
            session.setCurrentIndex(nextIndex);
        }

        return quizRepository.save(session);
    }
}
