package ru.otus.homework.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.homework.dataBase.core.model.User;
import ru.otus.homework.messagesystem.Message;
import ru.otus.homework.messagesystem.MessageType;
import ru.otus.homework.messagesystem.MsClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
public class FrontendServiceImpl implements FrontendService {
  private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

  private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
  private MsClient frontendMsClient;
  private final String databaseServiceClientName;

  public FrontendServiceImpl(@Qualifier("dbClientName") String databaseServiceClientName) {
    this.databaseServiceClientName = databaseServiceClientName;
  }

  @Autowired
  public void setFrontendMsClient(MsClient frontendMsClient) {
    this.frontendMsClient = frontendMsClient;
  }

  @Override
  public void getAllUsersData(Consumer<List<User>> dataConsumer) {
    Message outMsg = frontendMsClient.produceMessage(databaseServiceClientName, null, MessageType.ALL_USERS_DATA);
    consumerMap.put(outMsg.getId(), dataConsumer);
    frontendMsClient.sendMessage(outMsg);
  }

  @Override
  public void saveUserData(User user) {
    Message outMsg = frontendMsClient.produceMessage(databaseServiceClientName, user, MessageType.USER_DATA);
    frontendMsClient.sendMessage(outMsg);
  }

  @Override
  public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
    Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
    if (consumer == null) {
      logger.warn("consumer not found for:{}", sourceMessageId);
      return Optional.empty();
    }
    return Optional.of(consumer);
  }
}
