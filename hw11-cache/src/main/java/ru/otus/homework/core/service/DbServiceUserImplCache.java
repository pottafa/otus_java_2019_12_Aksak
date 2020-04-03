package ru.otus.homework.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.cache.HwCache;
import ru.otus.homework.core.dao.UserDao;
import ru.otus.homework.core.model.User;
import ru.otus.homework.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImplCache implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImplCache.class);

    private final UserDao userDao;
    private final HwCache<String, User> cache;

    public DbServiceUserImplCache(UserDao userDao, HwCache<String, User> cache) {
        this.userDao = userDao;
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.saveUser(user);
                sessionManager.commitSession();

                logger.info("created user: {}", userId);
                cache.put(String.valueOf(userId), user);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        Optional<User> userOptional = Optional.ofNullable(cache.get(String.valueOf(id)));
        if (userOptional.isEmpty()) {
            try (SessionManager sessionManager = userDao.getSessionManager()) {
                sessionManager.beginSession();
                try {
                    userOptional = userDao.findById(id);
                    cache.put(String.valueOf(id), userOptional.orElse(null));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    sessionManager.rollbackSession();
                }
            }
        }
        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;

    }

    @Override
    public void updateUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.updateUser(user);
                sessionManager.commitSession();
                logger.info("updated user: {}", user.getId());
                cache.put(String.valueOf(user.getId()), user);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


}
