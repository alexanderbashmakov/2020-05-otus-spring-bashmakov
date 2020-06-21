package ru.otus.spring.service;

import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceImpl implements IOService{

    private final PrintStream printStream;
    private final Scanner scanner;

    public IOServiceImpl(PrintStream printStream, Scanner scanner) {
        this.printStream = printStream;
        this.scanner = scanner;
    }


    @Override
    public void print(String text) {
        printStream.println(text);
    }

    @Override
    public String read() {
        return scanner.next();
    }
}
