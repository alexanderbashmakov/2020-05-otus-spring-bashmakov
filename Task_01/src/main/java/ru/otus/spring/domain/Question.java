package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class Question {
    private final String question;
    private final String correctAnswer;
    private final List<String> answers;
}
