package ru.otus.homework.jdbc.mapper.parser;

import java.util.StringJoiner;

public class SqlBuilder {
    private final static String SQL_INSERT = "insert into %s(%s) values (%s)";
    private final static String SQL_SELECT = "select %s,%s from %s where %s  = ?";
    private StringJoiner placeHolders = new StringJoiner(",");
    private StringJoiner values = new StringJoiner(",");
    private String tableName;
    private String id;

    SqlBuilder addTableName(String value) {
        if (tableName != null) return this;
        tableName = value;
        return this;
    }

    SqlBuilder addId(String value) {
        id = value;
        return this;
    }

    SqlBuilder addValue(String value) {
        values.add(value);
        return this;
    }

    SqlBuilder addPlaceholder() {
        placeHolders.add("?");
        return this;
    }

    public String buildInsertSql() {
        return String.format(SQL_INSERT, tableName, values, placeHolders);
    }

    public String buildSelectSql() {
        return String.format(SQL_SELECT, id, values, tableName, id);
    }

}
