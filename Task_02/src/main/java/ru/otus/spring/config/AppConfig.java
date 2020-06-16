package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.IOServiceImpl;

import java.io.PrintStream;
import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = "ru.otus.spring")
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    IOService ioService() {
        return new IOServiceImpl(new PrintStream(System.out), new Scanner(System.in));
    }

}
