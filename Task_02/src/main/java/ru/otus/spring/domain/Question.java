package ru.otus.spring.domain;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private final String question;
    private final String correctAnswer;
    private final List<String> answers;
}
