package ru.otus.spring.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс TestingResult")
@ExtendWith(SpringExtension.class)
class TestingResultTest {

    private TestingResult testingResult;

    @BeforeEach
    void init() {
        testingResult = new TestingResult(2);
    }

    @DisplayName("Проверка правильного ответа")
    @Test
    void checkCorrectAnswer() {
        testingResult.checkAnswer("OK", "OK");
        testingResult.checkAnswer("OK", "0");
        assertEquals(2, testingResult.getCorrectAnswers());
    }

    @DisplayName("Проверка неправильного ответа")
    @Test
    void checkIncorrectAnswer() {
        testingResult.checkAnswer("NONE", "OK");
        assertEquals(0, testingResult.getCorrectAnswers());
        testingResult.checkAnswer("NONE", "0");
        assertEquals(1, testingResult.getCorrectAnswers());
    }

    @DisplayName("Проверка результата")
    @Test
    void checkCorrectResult() {
        testingResult.checkAnswer("OK", "OK");
        testingResult.checkAnswer("NONE", "OK");
        assertTrue(testingResult.checkResult(50));
        assertFalse(testingResult.checkResult(60));
    }

}