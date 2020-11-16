package ru.otus.spring.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtusStudent {

    private Person person;
    private Boolean isAllTasksChecked;
    private Boolean isProjectDone;

}
