package ru.otus.dbServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class DbApp implements ApplicationRunner {

    @Autowired
    private DbSocketClient client;

    public static void main(String[] args) {
        SpringApplication.run(DbApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        client.go();
    }
}
