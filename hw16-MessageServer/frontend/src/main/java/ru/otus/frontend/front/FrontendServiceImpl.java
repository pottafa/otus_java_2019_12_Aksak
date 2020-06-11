package ru.otus.frontend.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.common.ms.MessageType;
import ru.otus.common.ms.MsClient;

import ru.otus.common.model.*;
import ru.otus.common.ms.Message;
import ru.otus.frontend.FrontendMsClient;
import ru.otus.frontend.FrontendSocketClient;


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
    private FrontendMsClient frontendMsClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(@Value("${database_service_client_name}") String databaseServiceClientName, @Lazy FrontendMsClient frontendMsClient) {
        this.databaseServiceClientName = databaseServiceClientName;
        this.frontendMsClient = frontendMsClient;
    }

    @Override
    public void getAllUsersData(Consumer<List<User>> dataConsumer) {
        Message outMsg = frontendMsClient.produceMessage(databaseServiceClientName, null, MessageType.ALL_USERS_DATA);
        consumerMap.put(outMsg.getId(), dataConsumer);
        frontendMsClient.sendMessage(outMsg);
    }

    @Override
    public void saveUserData(User user, Consumer<User> dataConsumer) {
        Message outMsg = frontendMsClient.produceMessage(databaseServiceClientName, user, MessageType.USER_DATA);
        consumerMap.put(outMsg.getId(), dataConsumer);
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
