package ru.otus.library.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        Calendar calendar = GregorianCalendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        if ((timeInMillis / 10000) % 2 == 0) {
            return Health.up().withDetail("message", "I'm alive!").build();
        } else {
            return Health.down().status(Status.DOWN).withDetail("message", "Алярм! Алярм!!!").build();
        }
    }
}
