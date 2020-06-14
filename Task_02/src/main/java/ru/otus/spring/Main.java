package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.config.appConfig;
import ru.otus.spring.service.QuestionService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(appConfig.class);
        QuestionService service = context.getBean(QuestionService.class);
        service.startInterview();
    }
}
