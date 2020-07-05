package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.User;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private User user;

    @Override
    public User authenticateUser(String name) {
        user = name != null ? new User(name) : null;
        return user;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean isLoggedIn() {
        return user != null;
    }
}
