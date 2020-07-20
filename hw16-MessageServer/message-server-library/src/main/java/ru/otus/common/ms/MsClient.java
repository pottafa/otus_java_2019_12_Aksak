package ru.otus.common.ms;

public interface MsClient {
    void sendMessage(Message message);

    void addHandler(MessageType type, RequestHandler requestHandler);

    void handle(Message msg);

    <T> Message produceMessage(String to, T data, MessageType msgType);

    String getName();
}
