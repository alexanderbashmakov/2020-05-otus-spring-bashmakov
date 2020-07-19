package ru.otus.library.service;

public interface MessageBundleService {
    String getMessage(String key);

    String getMessage(String key, Object... args);
}
