package ru.otus.homework.core.service;

import ru.otus.homework.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

    long saveAccount(Account account);

    Optional<Account> getAccount(long id);

    void updateAccount(Account account);

}
