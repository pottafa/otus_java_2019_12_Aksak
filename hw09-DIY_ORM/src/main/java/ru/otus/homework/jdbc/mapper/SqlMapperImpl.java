package ru.otus.homework.jdbc.mapper;

import ru.otus.homework.jdbc.mapper.parser.SqlBuilder;
import ru.otus.homework.jdbc.mapper.parser.JdbcParser;

import java.util.ArrayList;
import java.util.List;

public class SqlMapperImpl implements SqlMapper {
    private SqlBuilder builder = new SqlBuilder();
    private JdbcParser parser = new JdbcParser();
    private List<String> params;

    public List<String> getParams() {
        return params;
    }

    public String createSqlInsert(Object objectToParse) {
        params = new ArrayList<>();
        parser.parseObject(builder, objectToParse, params);
        return builder.buildInsertSql();
    }

    public String createSqlSelect(Class classData) {
        parser.parseClass(builder, classData);
        return builder.buildSelectSql();
    }

}
