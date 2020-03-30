package ru.otus.homework.core.dao;

import ru.otus.homework.core.model.User;
import ru.otus.homework.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long saveUser(User user);

    SessionManager getSessionManager();

}
