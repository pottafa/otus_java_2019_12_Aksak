package ru.otus.common.ms;


import java.util.Optional;

public interface RequestHandler {
    Optional<Message> handle(Message msg);
}
