package ru.otus.homework.services;

import ru.otus.homework.dataBase.core.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceUser.getUser(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
