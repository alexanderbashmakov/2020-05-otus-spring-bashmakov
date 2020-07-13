package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.service.IOService;
import ru.otus.library.service.IOServiceImpl;

import java.io.*;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс IOServiceImpl:")
@ExtendWith(SpringExtension.class)
class IOServiceImplTest {

    @DisplayName("выводит на экран")
    @Test
    public void print() {
        OutputStream outputStream = new ByteArrayOutputStream();
        IOService ioService = new IOServiceImpl(new PrintStream(outputStream));
        String testString = "testString";
        ioService.print(testString);
        assertThat(outputStream.toString()).contains(testString);
    }

}