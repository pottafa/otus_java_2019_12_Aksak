package ru.otus.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.ms.process.ProcessRunner;
import ru.otus.ms.socket.Server;

import java.io.IOException;

@SpringBootApplication
public class MessageServerApp implements ApplicationRunner {
    @Autowired
    private Server server;
    @Autowired
    ProcessRunner processRunner;
    private static final String FIRST_FRONTEND = "java -Dserver.port=8080 -jar ./hw16-MessageServer/frontend/target/Frontend-1.0-SNAPSHOT.jar";
    private static final String SECOND_FRONTEND = "java -Dserver.port=8081 -jar ./hw16-MessageServer/frontend/target/Frontend-1.0-SNAPSHOT.jar";
    private static final String DATABASE = "java -jar ./hw16-MessageServer/dbServer/target/DBServer-1.0-SNAPSHOT.jar";

    public static void main(String[] args) {
        SpringApplication.run(MessageServerApp.class, args);
    }

    public void run(ApplicationArguments args) throws IOException {
        processRunner.run(FIRST_FRONTEND);
        processRunner.run(SECOND_FRONTEND);
        processRunner.run(DATABASE);
        processRunner.run(DATABASE);
        server.go();
    }
}
