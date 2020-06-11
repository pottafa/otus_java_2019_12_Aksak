package ru.otus.ms.messagesystem;


import ru.otus.common.ms.Message;
import ru.otus.ms.socket.ClientThread;

public interface MessageSystem {

    void addClient(ClientThread clientThread);

    void removeClient(ClientThread clientThread);

    boolean newMessage(Message msg);

    void dispose() throws InterruptedException;

    void dispose(Runnable callback) throws InterruptedException;

    void start();

    int currentQueueSize();
}

