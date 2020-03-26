package ru.otus.homework.jdbc.mapper;

import java.util.List;

public interface SqlMapper {
    String createSqlSelect(Class<?> classToParse);

    String createSqlInsert(Object objectToParse);

    String createSqlUpdate(Object objectToParse);

    List<String> getParamsWithoutId();

    List<String> getParamsWithId();
}
