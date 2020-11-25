package ru.otus.library.repository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"ru.otus.library.repository"})
public class TestRepositoryConfig {
}
