package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

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