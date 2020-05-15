package ru.otus.homework.dataBase.handlers;

import ru.otus.homework.common.Serializers;
import ru.otus.homework.dataBase.core.model.User;
import ru.otus.homework.dataBase.core.service.DBServiceUser;
import ru.otus.homework.messagesystem.Message;
import ru.otus.homework.messagesystem.MessageType;
import ru.otus.homework.messagesystem.RequestHandler;

import java.util.Optional;

public class SaveUserHandler implements RequestHandler {
  DBServiceUser dbService;

  public SaveUserHandler(DBServiceUser dbService) {
    this.dbService = dbService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    User user = Serializers.deserialize(msg.getPayload(), User.class);
    dbService.saveUser(user);
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.USER_DATA.getValue(), Serializers.serialize(null)));
  }
}
