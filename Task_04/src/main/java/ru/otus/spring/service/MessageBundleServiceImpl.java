package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageBundleServiceImpl implements MessageBundleService {
    private final MessageSource messageSource;
    private final Locale locale;

    public MessageBundleServiceImpl(MessageSource messageSource, @Value("${locale:en}") String locale) {
        this.messageSource = messageSource;
        this.locale = new Locale(locale);
    }

    @Override
    public String getMessage(String key) {
        return messageSource.getMessage(key, new Object[]{}, locale);
    }

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }


}
