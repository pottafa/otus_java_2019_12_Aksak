package ru.otus.homework.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.core.dao.AccountDao;
import ru.otus.homework.core.dao.AccountDaoException;
import ru.otus.homework.core.model.Account;
import ru.otus.homework.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.DbExecutor;
import ru.otus.homework.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.homework.jdbc.mapper.SqlMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class AcoountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(AcoountDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<Account> dbExecutor;
    private final SqlMapper<Account> mapper;

    public AcoountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor, SqlMapper<Account> mapper) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.mapper = mapper;
    }


    @Override
    public Optional<Account> findById(long id) {
        try {
            return dbExecutor.selectRecord(getConnection(), mapper.createSqlSelect(Account.class), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return new Account(resultSet.getLong("no"), resultSet.getString("type"), resultSet.getInt("rest"));
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
    public long saveAccount(Account account) {
        try {
            return dbExecutor.insertRecord(getConnection(), mapper.createSqlInsert(account), mapper.getParams());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountDaoException(e);
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
