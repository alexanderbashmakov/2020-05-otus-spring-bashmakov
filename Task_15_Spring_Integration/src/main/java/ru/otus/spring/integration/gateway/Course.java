package ru.otus.spring.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.domain.OtusStudent;
import ru.otus.spring.integration.domain.SpringMaster;

import java.util.Collection;

@MessagingGateway
public interface Course {

    @Gateway(requestChannel = "startCourseChannel", replyChannel = "graduateChannel")
    SpringMaster process(OtusStudent student);
}
