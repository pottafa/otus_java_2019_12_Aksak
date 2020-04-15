package ru.otus.homework.dataBase.core.service;


import ru.otus.homework.dataBase.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    Optional<User> getUser(String login);

    List<User> getAllUsers();

}
