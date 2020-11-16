package ru.otus.spring.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.OtusStudent;

import java.util.Random;

@Service("studyService")
public class StudyServiceImpl {

    private final Random random = new Random();

    public OtusStudent checkTasks(OtusStudent student) throws Exception {
        System.out.println("Проверка домашних заданий");
        Thread.sleep(1000);
        student.setIsAllTasksChecked(random.nextInt() % 3 != 0);
        System.out.println("Проверка домашних заданий выполнена");
        return student;
    }

    public OtusStudent checkProjectWork(OtusStudent student) throws Exception {
        System.out.println("Защита проектной работы");
        Thread.sleep(1000);
        student.setIsProjectDone(random.nextInt() % 4 != 0);
        System.out.printf("Защита проектной работы %sвыполнена\n", student.getIsProjectDone() ? "" : "не ");
        return student;
    }
}
