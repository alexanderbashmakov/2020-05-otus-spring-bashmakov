package ru.otus.spring.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.integration.domain.OtusStudent;
import ru.otus.spring.integration.domain.Person;
import ru.otus.spring.integration.domain.SpringMaster;
import ru.otus.spring.integration.gateway.Course;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppTest {

    @Autowired
    private Course gateway;

    @Test
    public void gatewayTest() {
        SpringMaster master = gateway.process(OtusStudent.builder()
                .person(new Person("Person", 20))
                .build());
        assertThat(master).isNotNull();
    }
}