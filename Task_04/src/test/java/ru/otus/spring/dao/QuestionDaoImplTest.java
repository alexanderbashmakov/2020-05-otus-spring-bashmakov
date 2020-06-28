package ru.otus.spring.dao;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.config.TestConfig;
import ru.otus.spring.config.YmlConfig;
import ru.otus.spring.domain.Question;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDaoImpl")
@SpringBootTest
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = {QuestionDaoImpl.class, YmlConfig.class})
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