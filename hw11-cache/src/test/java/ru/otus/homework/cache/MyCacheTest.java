package ru.otus.homework.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.core.dao.UserDao;
import ru.otus.homework.core.model.User;
import ru.otus.homework.core.service.DBServiceUser;
import ru.otus.homework.core.service.DbServiceUserImplCache;
import ru.otus.homework.core.service.DbServiceUserImplNoCache;
import ru.otus.homework.h2.DataSourceH2;
import ru.otus.homework.jdbc.DbExecutor;
import ru.otus.homework.jdbc.dao.UserDaoJdbc;
import ru.otus.homework.jdbc.mapper.SqlMapper;
import ru.otus.homework.jdbc.mapper.SqlMapperImpl;
import ru.otus.homework.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MyCacheTest {
    String userTable = "create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))";
    final static long OBJECTS_AMOUNT = 1000;
    Connection connection;
    Logger logger = LoggerFactory.getLogger(MyCacheTest.class);
    HwCache<String, User> cache;
    DBServiceUser dbServiceWithCache;
    DBServiceUser dbServiceNoCache;

    @BeforeEach
    void setUp() throws SQLException {
        DataSource dataSource = new DataSourceH2();
        connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement(userTable);
        pst.executeUpdate();

        cache = new MyCache<>();
        HwListener<String, User> listener = new HwListener<String, User>() {
            @Override
            public void notify(String key, User value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };
        cache.addListener(listener);
        DbExecutor<User> dbExecutor = new DbExecutor<>();
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        SqlMapper mapper = new SqlMapperImpl();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor, mapper);
        dbServiceWithCache = new DbServiceUserImplCache(userDao, cache);
        dbServiceNoCache = new DbServiceUserImplNoCache(userDao);
    }

    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE USER");
        preparedStatement.execute();
        connection.close();
    }

    @Test
    @DisplayName("Speed comparison of using database with/without cache")
    void speedComparison() {
        long noCache = test(dbServiceNoCache);
        long withCache = test(dbServiceWithCache);
        logger.info("With cache time: {}, with no cache time: {}", withCache, noCache);
        assertTrue(noCache > withCache);
    }

    @Test
    @DisplayName("Save user")
    void saveUser() {
        User user = new User(0, "dbServiceUser", 35);
        long id = dbServiceWithCache.saveUser(user);
        User userFromCache = cache.get(String.valueOf(id));
        assertEquals(user, userFromCache);
    }

    @Test
    @DisplayName("Load existed user")
    void loadUser() {
        // logs
        User user = new User(0, "dbServiceUser", 35);
        long id = dbServiceWithCache.saveUser(user);
        Optional<User> optionalUser = dbServiceWithCache.getUser(id);
        assertEquals(user, optionalUser.orElse(null));
    }


    private long test(DBServiceUser dbServiceUser) {
        Instant start = Instant.now();
        List<Long> ids = createUsers(OBJECTS_AMOUNT, dbServiceUser);
        loadUsers(ids, dbServiceUser);
        Instant finish = Instant.now();
        return Duration.between(start, finish).toMillis();
    }

    public List<Long> createUsers(long amount, DBServiceUser dbServiceUser) {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i <= amount; i++) ids.add(dbServiceUser.saveUser(new User(i, "dbServiceUser", i)));
        return ids;
    }

    public void loadUsers(List<Long> ids, DBServiceUser dbServiceUser) {
        for (Long id : ids) {
            Optional<User> userOptional = dbServiceUser.getUser(id);
            userOptional.ifPresentOrElse(
                    user -> logger.info("created user, name:{}", user.getName()),
                    () -> logger.info("user was not created")
            );
        }
    }
}