package ru.otus.spring.dao;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.domain.Question;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDaoImpl")
@SpringBootTest
class QuestionDaoImplTest {

    @Autowired
    private QuestionDao questionDao;

    @Test
    @DisplayName("Получение вопросов")
    void getQuestionsTest() {
        assertThat(questionDao.getQuestions())
                .isNotEmpty()
                .asList()
                .hasSize(1)
                .asInstanceOf(InstanceOfAssertFactories.list(Question.class))
                .element(0)
                .extracting(Question::getQuestion)
                .isEqualTo("TestQuestion");
    }
}