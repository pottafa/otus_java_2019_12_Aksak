package ru.otus.ms.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.ms.messagesystem.MessageSystem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);
    private static final int PORT = 4004;
    final static ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);
    @Autowired
    private MessageSystem messageSystem;

    public void go() throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server Started");
            while (true) {
                Socket socket = server.accept();
                try {
                    ClientThread clientThread = new ClientThread(socket, messageSystem);
                    messageSystem.addClient(clientThread);
                    clientProcessingPool.submit(clientThread);
                } catch (IOException e) {
                    socket.close();
                }
            }
        }
    }
}
