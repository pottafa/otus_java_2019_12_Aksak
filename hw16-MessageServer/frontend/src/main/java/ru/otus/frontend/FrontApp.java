package ru.otus.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class FrontApp implements ApplicationRunner {
    @Autowired
    private FrontendSocketClient client;

    public static void main(String[] args) {
        SpringApplication.run(FrontApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        client.go();
    }
}
