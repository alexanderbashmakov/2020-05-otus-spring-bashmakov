package ru.otus.spring.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.UserAuthenticationService;
import ru.otus.spring.service.UserService;

@ShellComponent
public class TestingCommands {
    private final QuestionService questionService;
    private final UserService userService;
    private final UserAuthenticationService authenticationService;

    @Autowired
    public TestingCommands(QuestionService questionService, UserService userService, UserAuthenticationService authenticationService) {
        this.questionService = questionService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @ShellMethod(value = "StartTesting", key = {"s", "start"})
    @ShellMethodAvailability(value = "isMethodAvailable")
    public void startTesting() {
        questionService.startInterview();
    }

    @ShellMethod(value = "Logout", key = {"lo", "logout"})
    @ShellMethodAvailability(value = "isMethodAvailable")
    public void logout() {
        authenticationService.authenticateUser(null);
    }

    @ShellMethod(value = "Login", key={"l", "login"})
    @ShellMethodAvailability(value = "isMethodNotAvailable")
    public void login() {
        authenticationService.authenticateUser(userService.askName());
    }

    private Availability isMethodAvailable() {
        return authenticationService.isLoggedIn() ? Availability.available() : Availability.unavailable("user is not authenticated");
    }

    private Availability isMethodNotAvailable() {
        return authenticationService.isLoggedIn() ? Availability.unavailable("user already logged in") : Availability.available();
    }


}
