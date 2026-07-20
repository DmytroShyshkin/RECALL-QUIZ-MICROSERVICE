package com.dmytro.quiz_service.application.usercase.quiz;

import com.dmytro.quiz_service.domain.model.QuizQuestion;
import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.domain.ports.in.AnswerQuizUseCase;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import com.dmytro.quiz_service.domain.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AnswerQuizInteractor implements AnswerQuizUseCase {

    private final QuizRepositoryPort quizRepository;
    private final QuizService quizService;

    @Override
    public QuizSession answerQuiz(UUID sessionId, String userAnswer) {

        QuizSession session = quizRepository.findSessionById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));

        if (session.isCompleted()) {
            throw new IllegalStateException("Session already completed");
        }

        QuizQuestion current = session.getQuestions().get(session.getCurrentIndex());
        List<QuizQuestion> correctAnswers = new ArrayList<>();
        List<QuizQuestion> wrongAnswer = new ArrayList<>();

        current.setUserAnswer(userAnswer);

        boolean correct = quizService.checkAnswer(current, userAnswer);
        current.setAnsweredCorrectly(correct);

        if (correct) {
            session.setScore(session.getScore() + 1);
            correctAnswers.add(current);
            session.setCorrectAnswer(correctAnswers);
        }else {
            wrongAnswer.add(current);
            session.setWrongAnswer(wrongAnswer);
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