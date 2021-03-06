package ru.otus.homework.dataBase.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.homework.dataBase.core.dao.UserDao;
import ru.otus.homework.dataBase.core.dao.UserDaoException;
import ru.otus.homework.dataBase.core.model.User;
import ru.otus.homework.dataBase.core.sessionmanager.SessionManager;
import ru.otus.homework.dataBase.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.homework.dataBase.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoHibernate implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        User user = currentSession.getHibernateSession().createQuery("FROM User user where user.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
        return Optional.of(user);
    }

    public List<User> findAllUsers() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        return currentSession.getHibernateSession().createQuery("SELECT user FROM User user", User.class).getResultList();
    }


    @Override
    public long saveUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
            }
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

}
