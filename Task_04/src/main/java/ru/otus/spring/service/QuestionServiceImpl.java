package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.YmlConfig;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestingResult;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final IOService ioService;
    private final YmlConfig props;
    private final MessageBundleService messageBundleService;
    private TestingResult testingResult;

    @Override
    public void startInterview() {
        testingResult = new TestingResult(dao.getQuestions().size());
        String name = getStudentName();
        List<Question> questions = dao.getQuestions();
        questions.forEach(this::getAnswer);
        ioService.print(messageBundleService.getMessage("dear") + " " + name + ".");
        ioService.print(messageBundleService.getMessage(testingResult.checkResult(props.getMinCorrectAnswerPercent()) ? "passed" : "failed"));
    }

    public void getAnswer(Question question) {
        printQuestion(question);
        String answer = ioService.read();
        testingResult.checkAnswer(answer, question.getCorrectAnswer());
    }

    public void printQuestion(Question question) {
        ioService.print(question.getQuestion());
        List<String> answers = question.getAnswers();
        if (answers != null) {
            IntStream.range(0, answers.size()).forEach(i -> ioService.print(String.format("\t%d. %s", i + 1, answers.get(i))));
        }
    }

    private String getStudentName() {
        ioService.print(messageBundleService.getMessage("greeting"));
        return ioService.read();
    }
}
