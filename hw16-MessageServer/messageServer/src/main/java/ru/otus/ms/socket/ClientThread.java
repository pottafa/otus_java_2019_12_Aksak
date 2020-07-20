package ru.otus.ms.socket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.common.ms.Message;
import ru.otus.ms.messagesystem.MessageSystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
    private static Logger logger = LoggerFactory.getLogger(ClientThread.class);
    private Socket socket;
    private String clientName;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private MessageSystem messageSystem;

    public ClientThread(Socket socket, MessageSystem messageSystem) throws IOException {
        this.messageSystem = messageSystem;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            clientName = (String) in.readObject();
            logger.info("New connection: {}", clientName);
            while (true) {
                Message message = (Message) in.readObject();
                messageSystem.newMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
            logger.info("Message was sent successfully to: {}", msg.getTo());
        } catch (IOException ex) {
            logger.error("Message was not sent to: {}", msg.getTo(), ex);
        }
    }

    public String getClientName() {
        return clientName;
    }
}
