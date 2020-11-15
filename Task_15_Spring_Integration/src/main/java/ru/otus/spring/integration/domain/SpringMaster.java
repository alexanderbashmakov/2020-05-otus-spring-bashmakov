package ru.otus.spring.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpringMaster {

    private Person person;
    private boolean isQualified;
}
