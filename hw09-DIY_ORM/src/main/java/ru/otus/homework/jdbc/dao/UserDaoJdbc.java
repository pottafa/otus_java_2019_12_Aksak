package ru.otus.homework.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.core.dao.UserDao;
import ru.otus.homework.core.dao.UserDaoException;
import ru.otus.homework.core.model.User;
import ru.otus.homework.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.DbExecutor;
import ru.otus.homework.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.homework.jdbc.mapper.SqlMapper;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<User> dbExecutor;
    private final SqlMapper mapper;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor, SqlMapper mapper) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.mapper = mapper;
    }


    @Override
    public Optional<User> findById(long id) {
        try {
            return dbExecutor.selectRecord(getConnection(), mapper.createSqlSelect(User.class), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getInt("age"));
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Override
    public long saveUser(User user) {
        try {
            return dbExecutor.insertRecord(getConnection(), mapper.createSqlInsert(user), mapper.getParamsWithoutId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            dbExecutor.updateRecord(getConnection(), mapper.createSqlUpdate(user), mapper.getParamsWithId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }

    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
