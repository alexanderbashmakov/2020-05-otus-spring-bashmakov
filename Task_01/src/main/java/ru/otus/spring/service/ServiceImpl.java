package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionDao;

import java.io.IOException;

@RequiredArgsConstructor
public class ServiceImpl implements Service {

    private final QuestionDao dao;
    private final PrintService printer;

    @Override
    public void startInterview() throws IOException {
        dao.getQuestions().forEach(printer::print);
    }
}
