package ru.otus.dbServer.handlers;



import ru.otus.common.model.User;
import ru.otus.common.ms.Message;
import ru.otus.common.ms.MessageType;
import ru.otus.common.ms.RequestHandler;
import ru.otus.common.ms.common.Serializers;
import ru.otus.dbServer.core.service.DBServiceUser;

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
