package ru.otus.homework.dataBase.core.dao;

import ru.otus.homework.dataBase.core.model.User;
import ru.otus.homework.dataBase.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    Optional<User> findByLogin(String login);

    List<User> findAllUsers();

    long saveUser(User user);

    SessionManager getSessionManager();
}
