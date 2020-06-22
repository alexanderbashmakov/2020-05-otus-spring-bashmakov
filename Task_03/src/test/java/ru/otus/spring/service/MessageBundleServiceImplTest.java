package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.config.TestConfig;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс MessageBundleServiceImpl")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = {MessageBundleServiceImpl.class, TestConfig.class})
class MessageBundleServiceImplTest {

    @Autowired
    private MessageBundleService messageBundleService;

    @DisplayName("Проверка вывода сообщения из файла bundle")
    @Test
    public void getMessageTest() {
        assertThat(messageBundleService.getMessage("test-message1")).isEqualTo("test message 1");
    }

}