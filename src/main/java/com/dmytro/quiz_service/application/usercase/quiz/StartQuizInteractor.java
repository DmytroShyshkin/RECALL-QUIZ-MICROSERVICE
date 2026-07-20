package com.dmytro.quiz_service.application.usercase.quiz;

import com.dmytro.quiz_service.domain.model.QuizQuestion;
import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.domain.ports.in.StartQuizUseCase;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import com.dmytro.quiz_service.domain.ports.out.WordsProviderPort;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;
import com.dmytro.quiz_service.domain.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StartQuizInteractor implements StartQuizUseCase {

    private final WordsProviderPort wordsProvider;
    private final QuizRepositoryPort quizRepository;
    private final QuizService quizService;

    @Override
    public QuizSession startQuiz(UUID userId, String jwt, List<String> languages, int wordCount) {

        List<WordsDTO> allWords = new ArrayList<>();
        for (String lang : languages) {
            wordsProvider.getWordsByUser(jwt, lang).stream()
                    .filter(word -> languages.contains(word.sourceLanguage()))
                    .forEach(allWords::add);
        }

        if (allWords.isEmpty()) {
            throw new IllegalStateException("No words available for quiz");
        }

        Collections.shuffle(allWords);
        List<WordsDTO> selected = allWords.stream()
                .limit(wordCount)
                .toList();

        List<QuizQuestion> questions = quizService.buildQuestions(selected);

        QuizSession session = QuizSession.builder()
                .sessionId(UUID.randomUUID())
                .userId(userId)
                .startedAt(LocalDateTime.now())
                .questions(questions)
                .currentIndex(0)
                .score(0)
                .completed(false)
                .correctAnswer(new ArrayList<>())
                .wrongAnswer(new ArrayList<>())
                .build();

        return quizRepository.save(session);
    }
}