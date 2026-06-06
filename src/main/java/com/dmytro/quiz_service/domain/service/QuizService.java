package com.dmytro.quiz_service.domain.service;

import com.dmytro.quiz_service.domain.model.QuizQuestion;
import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.domain.ports.in.AnswerQuizUseCase;
import com.dmytro.quiz_service.domain.ports.in.GetQuizResultUseCase;
import com.dmytro.quiz_service.domain.ports.in.StartQuizUseCase;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import com.dmytro.quiz_service.domain.ports.out.UserProgressPort;
import com.dmytro.quiz_service.domain.ports.out.WordsProviderPort;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuizService implements StartQuizUseCase, AnswerQuizUseCase, GetQuizResultUseCase {

    private final WordsProviderPort wordsProvider;
    private final QuizRepositoryPort quizRepository;
    private final UserProgressPort userProgress;

    @Override
    public QuizSession startQuiz(UUID userId, String jwt, List<String> languages, int wordCount) {
        List<WordsDTO> allWords = new ArrayList<>();
        for (String lang : languages) {
            allWords.addAll(wordsProvider.getWordsByUser(jwt, lang));
        }

        if (allWords.isEmpty()) {
            throw new IllegalStateException("No words available for quiz");
        }

        Collections.shuffle(allWords);
        List<WordsDTO> selected = allWords.stream()
                .limit(wordCount)
                .toList();

        List<QuizQuestion> questions = selected.stream()
                .map(word -> buildQuestion(word, allWords))
                .toList();

        QuizSession session = QuizSession.builder()
                .sessionId(UUID.randomUUID())
                .userId(userId)
                .startedAt(LocalDateTime.now())
                .questions(questions)
                .currentIndex(0)
                .score(0)
                .completed(false)
                .build();

        return quizRepository.save(session);
    }

    @Override
    public QuizSession answerQuiz(UUID sessionId, String userAnswer) {
        QuizSession session = quizRepository.findSessionById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));

        if (session.isCompleted()) {
            throw new IllegalStateException("Session already completed");
        }

        QuizQuestion current = session.getQuestions().get(session.getCurrentIndex());
        current.setUserAnswer(userAnswer);

        boolean correct = current.getCorrectAnswer().equalsIgnoreCase(userAnswer.trim());
        current.setAnsweredCorrectly(correct);

        if (correct) {
            session.setScore(session.getScore() + 1);
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

    @Override
    public QuizSession getQuizResult(UUID sessionId) {
        return quizRepository.findSessionById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));
    }

    private QuizQuestion buildQuestion(WordsDTO word, List<WordsDTO> allWords) {
        String correct = word.translations().isEmpty()
                ? word.originalWord()
                : word.translations().getFirst().translatedWord();

        List<String> wrong = allWords.stream()
                .filter(w -> !w.originalWord().equals(word.originalWord()))
                .map(w -> w.translations().isEmpty()
                        ? w.originalWord()
                        : w.translations().getFirst().translatedWord())
                .distinct()
                .limit(3)
                .toList();

        List<String> options = new ArrayList<>(wrong);
        options.add(correct);
        Collections.shuffle(options);

        return QuizQuestion.builder()
                .wordId(UUID.randomUUID())
                .question(word.originalWord())
                .options(options)
                .correctAnswer(correct)
                .language(List.of(word.sourceLanguage()))
                .build();
    }
}
