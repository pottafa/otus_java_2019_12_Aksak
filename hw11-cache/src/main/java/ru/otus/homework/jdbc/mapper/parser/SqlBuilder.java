package ru.otus.homework.jdbc.mapper.parser;

import ru.otus.homework.jdbc.mapper.SqlMapperException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class SqlBuilder {
    private final static String SQL_INSERT = "insert into %s(%s) values (%s)";
    private final static String SQL_SELECT = "select %s,%s from %s where %s  = ?";
    private final static String SQL_UPDATE_QUERY = "update %s set %s where %s=?";
    private List<String> values = new ArrayList<>();
    private String tableName;
    private String id;

    SqlBuilder addTableName(String value) {
        if (tableName != null) return this;
        tableName = value;
        return this;
    }

    SqlBuilder addId(String value) {
        if(id != null) throw new SqlMapperException("More than one if field");
        id = value;
        return this;
    }

    SqlBuilder addValue(String value) {
        values.add(value);
        return this;
    }

   private StringJoiner getPlaceholder() {
       StringJoiner placeHolders = new StringJoiner(",");
        for(int i = 0; i < values.size(); i++) {
            placeHolders.add("?");
        }
        return placeHolders;
    }

    public String buildInsertSql() {
        String valuesToInsert = String.join(",", values);
        return String.format(SQL_INSERT, tableName, valuesToInsert, getPlaceholder());
    }

    public String buildSelectSql() {
        if(id == null)  throw new SqlMapperException("Id field is not found");
        String valuesToInsert = String.join(",", values);
        return String.format(SQL_SELECT, id, valuesToInsert, tableName, id);
    }

    public String buildUpdateSql() {
        if(id == null)  throw new SqlMapperException("Id field is not found");
        String valuesToInsert = values.stream()
                .map(value -> value + "=?")
                .collect(Collectors.joining(","));
        return String.format(SQL_UPDATE_QUERY, tableName, valuesToInsert, id);
    }

}
