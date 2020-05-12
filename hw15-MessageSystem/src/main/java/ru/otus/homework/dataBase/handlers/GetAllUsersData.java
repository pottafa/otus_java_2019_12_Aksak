package ru.otus.homework.dataBase.handlers;

import ru.otus.homework.common.Serializers;
import ru.otus.homework.dataBase.core.model.User;
import ru.otus.homework.dataBase.core.service.DBServiceUser;
import ru.otus.homework.messagesystem.Message;
import ru.otus.homework.messagesystem.MessageType;
import ru.otus.homework.messagesystem.RequestHandler;

import java.util.List;
import java.util.Optional;

public class GetAllUsersData implements RequestHandler {
  DBServiceUser dbService;

  public GetAllUsersData(DBServiceUser dbService) {
    this.dbService = dbService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    List<User> userList = dbService.getAllUsers();
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.ALL_USERS_DATA.getValue(), Serializers.serialize(userList)));
  }
}
