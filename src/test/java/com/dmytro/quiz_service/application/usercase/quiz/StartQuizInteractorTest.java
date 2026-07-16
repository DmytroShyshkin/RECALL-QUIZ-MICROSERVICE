package com.dmytro.quiz_service.application.usercase.quiz;

import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import com.dmytro.quiz_service.domain.ports.out.WordsProviderPort;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;
import com.dmytro.quiz_service.domain.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("StartQuizInteractor Unit Test")
@RequiredArgsConstructor
class StartQuizInteractorTest {

    @Mock
    private final WordsProviderPort wordsProvider;
    @Mock
    private final QuizRepositoryPort quizRepository;
    @InjectMocks
    private final QuizService quizService;

    @Test
    void shouldStartQuizTestSuccessfully() {
        // Given
        final UUID userId = UUID.randomUUID();
        final String jwt = UUID.randomUUID().toString();
        final List<String> languages = new ArrayList<>(
                List.of(
                        "Hello"
                        , "Epic"
                        , "Wrong"
                        , "Peste"
                        , "Acompañar"
                ));
        final int wordCount;

        // When
        List<WordsDTO> allWords = new ArrayList<>();

        for (String lang : languages) {
            when(allWords.addAll(wordsProvider.getWordsByUser(jwt, lang)));
        }

        //Then

    }
}