package ru.otus.spring.service;

import ru.otus.spring.domain.User;

public interface UserAuthenticationService {
    User authenticateUser(String name);
    User getUser();
    boolean isLoggedIn();
}
