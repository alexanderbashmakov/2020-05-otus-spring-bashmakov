package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class IOServiceImpl implements IOService{

    private final PrintStream printStream;

    public IOServiceImpl(@Value("#{ T(java.lang.System).out}") PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void print(String text) {
        printStream.println(text);
    }
}
