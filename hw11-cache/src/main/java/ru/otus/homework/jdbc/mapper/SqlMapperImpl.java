package ru.otus.homework.jdbc.mapper;

import ru.otus.homework.jdbc.mapper.parser.JdbcParser;
import ru.otus.homework.jdbc.mapper.parser.SqlBuilder;

import java.util.ArrayList;
import java.util.List;

public class SqlMapperImpl implements SqlMapper {
    private SqlBuilder builder;
    private JdbcParser parser;

    public List<String> getParamsWithoutId() {
        return parser.getParams();
    }

    public List<String> getParamsWithId() {
        List<String> params = new ArrayList<>(getParamsWithoutId());
        params.add(parser.getIdValue());
        return params;
    }

    public String createSqlInsert(Object objectToParse) {
        builder = new SqlBuilder();
        parser = new JdbcParser();
        parser.parseObject(builder, objectToParse);
        return builder.buildInsertSql();
    }

    public String createSqlSelect(Class classData) {
        builder = new SqlBuilder();
        parser = new JdbcParser();
        parser.parseClass(builder, classData);
        return builder.buildSelectSql();
    }

    public String createSqlUpdate(Object objectToParse) {
        builder = new SqlBuilder();
        parser = new JdbcParser();
        parser.parseObject(builder, objectToParse);
        return builder.buildUpdateSql();
    }

}
