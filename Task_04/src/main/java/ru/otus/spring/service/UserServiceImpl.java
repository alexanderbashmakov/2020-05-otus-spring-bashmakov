package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final IOService ioService;
    private final MessageBundleService messageBundleService;

    @Override
    public String askName() {
        ioService.print(messageBundleService.getMessage("greeting"));
        return ioService.read();
    }
}
