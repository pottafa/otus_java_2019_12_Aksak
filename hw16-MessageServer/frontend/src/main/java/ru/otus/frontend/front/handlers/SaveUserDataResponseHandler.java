package ru.otus.frontend.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.common.model.*;
import ru.otus.common.ms.Message;
import ru.otus.common.ms.RequestHandler;
import ru.otus.frontend.front.FrontendService;

import java.util.Optional;
import java.util.UUID;

public class SaveUserDataResponseHandler implements RequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(SaveUserDataResponseHandler.class);
  private final FrontendService frontendService;

  public SaveUserDataResponseHandler(FrontendService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
      frontendService.takeConsumer(sourceMessageId, User.class).ifPresent(consumer -> consumer.accept(null));

    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
