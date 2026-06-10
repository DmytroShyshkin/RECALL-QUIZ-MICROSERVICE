package com.dmytro.quiz_service.domain.service;

import com.dmytro.quiz_service.domain.model.QuizQuestion;
import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class QuizService {

    public List<QuizQuestion> buildQuestions(List<WordsDTO> words) {
        return words.stream()
                .map(word -> buildQuestion(word, words))
                .toList();
    }

    public QuizQuestion buildQuestion(WordsDTO word, List<WordsDTO> allWords) {
        String correct = word.translations().isEmpty()
                ? word.originalWord()
                : word.translations().get(0).translatedWord();

        List<String> wrong = allWords.stream()
                .filter(w -> !w.originalWord().equals(word.originalWord()))
                .map(w -> w.translations().isEmpty()
                        ? w.originalWord()
                        : w.translations().get(0).translatedWord())
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

    public boolean checkAnswer(QuizQuestion question, String userAnswer) {
        return question.getCorrectAnswer().equalsIgnoreCase(userAnswer.trim());
    }
}