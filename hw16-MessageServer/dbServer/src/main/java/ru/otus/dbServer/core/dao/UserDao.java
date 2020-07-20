package ru.otus.dbServer.core.dao;



import ru.otus.common.model.User;
import ru.otus.dbServer.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    Optional<User> findByLogin(String login);

    List<User> findAllUsers();

    long saveUser(User user);

    SessionManager getSessionManager();
}
