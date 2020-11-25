package ru.otus.library;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@SpringBootApplication
@EnableMongoRepositories(basePackages = "ru.otus.library.repository")
@EnableConfigurationProperties
@EnableCircuitBreaker
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

