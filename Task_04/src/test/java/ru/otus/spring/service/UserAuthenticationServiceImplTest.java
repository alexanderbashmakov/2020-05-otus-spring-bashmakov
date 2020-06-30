package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс UserAuthenticationServiceImpl")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserAuthenticationServiceImpl.class})
class UserAuthenticationServiceImplTest {

    @Autowired
    private UserAuthenticationService authenticationService;

    @Test
    @DisplayName("Метод authenticateUser")
    public void authenticateUserTest() {
        User user = authenticationService.authenticateUser("User");
        assertThat(user.getName()).isEqualTo("User");
    }
}