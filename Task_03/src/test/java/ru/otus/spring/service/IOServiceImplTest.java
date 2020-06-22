package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс IOServiceImpl")
class IOServiceImplTest {

    private final String testString = "testString";

    @DisplayName("print")
    @Test
    public void print() {
        OutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new ByteArrayInputStream(new byte[]{});
        IOService ioService = new IOServiceImpl(new PrintStream(outputStream), new Scanner(inputStream));
        ioService.print(testString);
        assertThat(outputStream.toString()).contains(testString);
    }

    @DisplayName("read")
    @Test
    public void read() {
        OutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
        IOService ioService = new IOServiceImpl(new PrintStream(outputStream), new Scanner(inputStream));
        assertThat(ioService.read()).contains(testString);
    }
}