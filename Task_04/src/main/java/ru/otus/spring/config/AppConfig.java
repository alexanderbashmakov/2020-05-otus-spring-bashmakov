package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.IOServiceImpl;

import java.io.PrintStream;
import java.util.Scanner;

@Component
public class AppConfig {

    @Bean
    IOService ioService() {
        return new IOServiceImpl(new PrintStream(System.out), new Scanner(System.in));
    }

}
