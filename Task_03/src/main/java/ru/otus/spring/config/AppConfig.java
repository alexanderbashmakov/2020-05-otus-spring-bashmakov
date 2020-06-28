package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.IOServiceImpl;

import java.io.PrintStream;
import java.util.Scanner;

@EnableAspectJAutoProxy
@Configuration
@EnableConfigurationProperties
public class AppConfig {

    @Bean
    IOService ioService() {
        return new IOServiceImpl(new PrintStream(System.out), new Scanner(System.in));
    }

    @ConfigurationProperties(prefix = "application")
    @Bean
    public YmlConfig prop() { return new YmlConfig(); }


}
