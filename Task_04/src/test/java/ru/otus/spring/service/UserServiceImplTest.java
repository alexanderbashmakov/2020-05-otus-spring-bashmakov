package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserServiceImpl.class})
@DisplayName("Класс UserServiceImpl")
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private IOService ioService;

    @MockBean
    private MessageBundleService messageBundleService;

    @Test
    @DisplayName("Получение имени пользователя")
    public void askNameTest() {
        when(messageBundleService.getMessage("greeting")).thenReturn("greeting");
        userService.askName();
        verify(ioService).print("greeting");
        verify(ioService).read();
    }
}