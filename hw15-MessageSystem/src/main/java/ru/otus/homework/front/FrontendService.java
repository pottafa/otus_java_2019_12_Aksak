package ru.otus.homework.front;


import ru.otus.homework.dataBase.core.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
   void saveUserData(User user, Consumer<User> dataConsumer);
   void getAllUsersData(Consumer<List<User>> dataConsumer);

    <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}

