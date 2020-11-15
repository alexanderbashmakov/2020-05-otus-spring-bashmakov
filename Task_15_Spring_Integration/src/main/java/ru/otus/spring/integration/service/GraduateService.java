package ru.otus.spring.integration.service;

import ru.otus.spring.integration.domain.OtusStudent;
import ru.otus.spring.integration.domain.SpringMaster;

public interface GraduateService {
    SpringMaster positiveGraduate(OtusStudent student) throws Exception;
    SpringMaster negativeGraduate(OtusStudent student) throws Exception;
}
