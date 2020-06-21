package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final IOService ioService;
    private final float correctAnswerCountPercent;

    public QuestionServiceImpl(QuestionDao dao,
                               IOService ioService,
                               @Value("${questions.min-correct-answer-percent}") float correctAnswerCountPercent) {
        this.dao = dao;
        this.ioService = ioService;
        this.correctAnswerCountPercent = correctAnswerCountPercent;
    }

    @Override
    public void startInterview() {
        ioService.print("Enter your name:");
        final String name = ioService.read();
        int correctAnswerCount = 0;

        List<Question> questions = dao.getQuestions();
        for (Question question : questions) {
            printQuestion(question);
            String answer = ioService.read();
            if (question.getCorrectAnswer().equals("0") || question.getCorrectAnswer().equals(answer)) {
                correctAnswerCount++;
            }
        }
        ioService.print("Dear " + name + ".");
        ioService.print("You " + (isPassed(correctAnswerCount) ? "passed" : "failed") + " the test.");
    }

    private boolean isPassed(int correctAnswerCount) {
        return 1.0 * correctAnswerCount / dao.getQuestions().size() >= correctAnswerCountPercent / 100;
    }

    public void printQuestion(Question question) {
        ioService.print(question.getQuestion());
        question.getAnswers().forEach(ioService::print);
    }

}
