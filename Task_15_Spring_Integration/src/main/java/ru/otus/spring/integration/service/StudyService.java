package ru.otus.spring.integration.service;

import ru.otus.spring.integration.domain.OtusStudent;

public interface StudyService {
    OtusStudent checkTasks(OtusStudent student);
    OtusStudent checkProjectWork(OtusStudent student);
}
