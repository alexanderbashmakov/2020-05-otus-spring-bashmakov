package ru.otus.spring.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpringMaster {

    private Person person;
    private boolean isQualified;
}
