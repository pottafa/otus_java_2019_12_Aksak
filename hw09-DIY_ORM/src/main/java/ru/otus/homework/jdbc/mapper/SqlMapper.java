package ru.otus.homework.jdbc.mapper;

import java.util.List;

public interface SqlMapper<T> {
    String createSqlSelect(Class<?> classToParse);

    String createSqlInsert(Object objectToParse);

    List<String> getParams();
}
