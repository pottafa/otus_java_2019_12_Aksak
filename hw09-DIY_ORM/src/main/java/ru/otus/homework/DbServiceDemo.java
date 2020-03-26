package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.core.dao.AccountDao;
import ru.otus.homework.core.dao.UserDao;
import ru.otus.homework.core.model.Account;
import ru.otus.homework.core.model.User;
import ru.otus.homework.core.service.DBServiceAccount;
import ru.otus.homework.core.service.DBServiceUser;
import ru.otus.homework.core.service.DbServiceAccountImpl;
import ru.otus.homework.core.service.DbServiceUserImpl;
import ru.otus.homework.h2.DataSourceH2;
import ru.otus.homework.jdbc.DbExecutor;
import ru.otus.homework.jdbc.dao.AcoountDaoJdbc;
import ru.otus.homework.jdbc.dao.UserDaoJdbc;
import ru.otus.homework.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.homework.jdbc.mapper.SqlMapper;
import ru.otus.homework.jdbc.mapper.SqlMapperImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;


/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSourceH2();
        DbServiceDemo demo = new DbServiceDemo();

        // User
        String userTable = "create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))";
        demo.createTable(dataSource, userTable);
        DbExecutor<User> dbExecutor = new DbExecutor<>();
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        SqlMapper mapper = new SqlMapperImpl();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor, mapper);
        DBServiceUser dbService = new DbServiceUserImpl(userDao);
        long id = dbService.saveUser(new User(2, "dbServiceUser", 34));
        Optional<User> user = dbService.getUser(id);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

dbService.updateUser(new User(id, "updatedUser", 34));

        Optional<User> updatedUser = dbService.getUser(id);
        updatedUser.ifPresentOrElse(
                crUser -> logger.info("updated user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        // Account
        String accountTable = "create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)";
        demo.createTable(dataSource, accountTable);

        SessionManagerJdbc accountSessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<Account> accountDbExecutor = new DbExecutor<>();
        SqlMapper accountMapper = new SqlMapperImpl();
        AccountDao accountDao = new AcoountDaoJdbc(accountSessionManager, accountDbExecutor, accountMapper);

        DBServiceAccount accountDbService = new DbServiceAccountImpl(accountDao);

        long accountId = accountDbService.saveAccount(new Account(0, "testAccount", 1));

        Optional<Account> account = accountDbService.getAccount(accountId);

        System.out.println(account);
        account.ifPresentOrElse(
                crUser -> logger.info("created account, type:{}", crUser.getType()),
                () -> logger.info("account was not created")
        );

    }

    private void createTable(DataSource dataSource, String table) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(table)) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
