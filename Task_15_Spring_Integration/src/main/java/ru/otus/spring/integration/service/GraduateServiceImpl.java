package ru.otus.spring.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.OtusStudent;
import ru.otus.spring.integration.domain.SpringMaster;

@Service("graduateService")
public class GraduateServiceImpl implements GraduateService {

    @Override
    public SpringMaster positiveGraduate(OtusStudent student) throws Exception{
        Thread.sleep(1000);
        System.out.printf("Студент %s прошел обучение успешно\n", student.getPerson().getName());
        return SpringMaster.builder()
                .person(student.getPerson())
                .isQualified(true)
                .build();
    }

    @Override
    public SpringMaster negativeGraduate(OtusStudent student) throws Exception{
        Thread.sleep(1000);
        System.out.printf("Студент %s не прошел обучение\n", student.getPerson().getName());
        return SpringMaster.builder()
                .person(student.getPerson())
                .isQualified(false)
                .build();
    }
}
