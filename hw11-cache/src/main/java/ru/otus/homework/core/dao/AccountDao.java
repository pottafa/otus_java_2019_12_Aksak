package ru.otus.homework.core.dao;

import ru.otus.homework.core.model.Account;
import ru.otus.homework.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findById(long id);

    long saveAccount(Account account);

    SessionManager getSessionManager();

    void updateAccount(Account account);
}
