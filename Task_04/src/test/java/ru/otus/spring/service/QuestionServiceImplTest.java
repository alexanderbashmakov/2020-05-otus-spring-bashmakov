package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.YmlConfig;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Класс QuestionServiceImpl")
@SpringBootTest(classes = {QuestionServiceImpl.class, YmlConfig.class})
class QuestionServiceImplTest {

    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private UserAuthenticationService userAuthenticationService;

    @MockBean
    private IOService ioService;

    @MockBean
    private MessageBundleService messageBundleService;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("startInterview")
    public void startInterviewTest() {
        when(userAuthenticationService.getUser()).thenReturn(new User("User"));
        when(messageBundleService.getMessage(eq("dear"), eq("User"))).thenReturn("Dear User");
        Question question = new Question("TestQuestion", "2", Arrays.asList("Answer1", "Answer2", "Answer3"));
        when(questionDao.getQuestions()).thenReturn(Collections.singletonList(question));
        questionService.startInterview();
        verify(ioService, atLeastOnce()).read();
        verify(ioService, atLeastOnce()).print("Dear User");
        //verify().getQuestions();

        assertThat(true);
    }
}