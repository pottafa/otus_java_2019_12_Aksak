package ru.otus.frontend.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.common.model.*;
import ru.otus.common.ms.common.*;
import ru.otus.common.ms.Message;
import ru.otus.common.ms.RequestHandler;
import ru.otus.frontend.front.FrontendService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetAllUsersDataResponseHandler implements RequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(GetAllUsersDataResponseHandler.class);
  private final FrontendService frontendService;

  public GetAllUsersDataResponseHandler(FrontendService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      List<User> userData = Serializers.deserialize(msg.getPayload(), List.class);
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
      frontendService.takeConsumer(sourceMessageId, List.class).ifPresent(consumer -> consumer.accept(userData));

    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
