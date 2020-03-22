package ru.otus.homework.jdbc.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.core.model.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SqlMapperImplTest {
    User user;
    SqlMapperImpl mapper;

    @BeforeEach
    void setUp() {
        user = new User(5, "Alex", 35);
    }

    @Test
    @DisplayName("Get params withoud ID field")
    void getParamsWithoutId() {
        mapper = new SqlMapperImpl();
        mapper.createSqlInsert(user);
        List<String> listParams = mapper.getParams();
        List<String> expected = Arrays.asList("Alex", "35");

        System.out.println(Arrays.toString(listParams.toArray()));
        assertArrayEquals(expected.toArray(), listParams.toArray());

    }

    @Test
    @DisplayName("Get insert sql request")
    void getSqlInsert() {
        mapper = new SqlMapperImpl();
        assertEquals("insert into user(name,age) values (?,?)", mapper.createSqlInsert(user));
    }

    @Test
    @DisplayName("Get select sql request")
    void getSqlSelect() {
        mapper = new SqlMapperImpl();
        assertEquals("select id,name,age from user where id  = ?", mapper.createSqlSelect(User.class));
    }
}