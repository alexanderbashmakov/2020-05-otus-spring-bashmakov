package ru.otus.spring.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import ru.otus.spring.integration.domain.OtusStudent;
import ru.otus.spring.integration.domain.Person;
import ru.otus.spring.integration.gateway.Course;

import javax.annotation.PostConstruct;


@IntegrationComponentScan
@SuppressWarnings({"resource", "Duplicates", "InfiniteLoopStatement"})
@SpringBootApplication
@RequiredArgsConstructor
public class App {
    @Qualifier("noPassedTasksChannel")
    private final PublishSubscribeChannel noPassedTasksChannel;

    @Qualifier("noPassedProjectChannel")
    private final PublishSubscribeChannel noPassedProjectChannel;

    @PostConstruct
    public void subscribeOnChannels() {
        noPassedTasksChannel.subscribe(message ->
                System.out.printf("Студент %s не сдал все домашние задания\n", ((OtusStudent) message.getPayload()).getPerson().getName()));
        noPassedProjectChannel.subscribe(message ->
                System.out.printf("Студент %s не прошел защиту проектной работы\n", ((OtusStudent) message.getPayload()).getPerson().getName()));
    }

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
        //AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        Course course = ctx.getBean(Course.class);


        for (int i = 0; i < 10; i++) {
            final Person person = new Person("Student" + i, 20 + i);
            System.out.println(course.process(new OtusStudent(person, false, false)));
        }
    }
}
