package ru.otus.spring.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.QuestionService;

//@Service
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {
    private final QuestionService questionService;
    @Override
    public void run(ApplicationArguments args) {
        questionService.startInterview();
    }
}
