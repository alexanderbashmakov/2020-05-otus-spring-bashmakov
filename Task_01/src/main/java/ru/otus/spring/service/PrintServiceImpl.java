package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

public class PrintServiceImpl implements PrintService{

    @Override
    public void print(Question question) {
        System.out.println(question.getQuestion());
        question.getAnswers().forEach(System.out::println);
        System.out.println();
    }
}
