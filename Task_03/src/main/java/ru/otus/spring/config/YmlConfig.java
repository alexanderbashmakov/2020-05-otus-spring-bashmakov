package ru.otus.spring.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YmlConfig {
    private String fileName;
    private float minCorrectAnswerPercent;
}
