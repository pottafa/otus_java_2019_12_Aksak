package ru.otus.dbServer.handlers;


import ru.otus.common.model.User;
import ru.otus.common.ms.Message;
import ru.otus.common.ms.MessageType;
import ru.otus.common.ms.RequestHandler;
import ru.otus.common.ms.common.Serializers;
import ru.otus.dbServer.core.service.DBServiceUser;

import java.util.List;
import java.util.Optional;

public class GetAllUsersData implements RequestHandler {
  private final DBServiceUser dbService;

  public GetAllUsersData(DBServiceUser dbService) {
    this.dbService = dbService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    List<User> userList = dbService.getAllUsers();
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.ALL_USERS_DATA.getValue(), Serializers.serialize(userList)));
  }
}
