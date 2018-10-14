package com.flowacademy.presencemanagerspringwifiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PresenceManagerSpringWifiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresenceManagerSpringWifiServerApplication.class, args);
    }
}
