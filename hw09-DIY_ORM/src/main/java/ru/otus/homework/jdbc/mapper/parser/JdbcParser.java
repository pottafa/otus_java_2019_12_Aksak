package ru.otus.homework.jdbc.mapper.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import static ru.otus.homework.jdbc.mapper.parser.ParserTypes.getType;

public class JdbcParser {

    public void parseObject(SqlBuilder builder, Object objectToParse, List<String> params) {
        switch (getType(objectToParse)) {
            case OBJECT: {
                builder.addTableName(objectToParse.getClass().getSimpleName().toLowerCase());
                var declaredFields = objectToParse.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Id.class)) {
                        builder.addId(field.getName());
                        continue;
                    }
                    builder.addValue(field.getName());
                    builder.addPlaceholder();
                    try {
                        if (field.get(objectToParse) == null) {
                            params.add(null);
                            continue;
                        }
                        if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL && (field.getModifiers() & Modifier.STATIC) == Modifier.STATIC)
                            continue;
                        parseObject(builder, field.get(objectToParse), params);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            }

            case STRING: {
                String fieldParam = (String) objectToParse;
                params.add(fieldParam);
                break;
            }
            case PRIMITIVE: {
                params.add(objectToParse.toString());
                break;
            }
        }
    }

    public void parseClass(SqlBuilder builder, Class<?> classToParse) {
        builder.addTableName(classToParse.getSimpleName().toLowerCase());
        var declaredFields = classToParse.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                builder.addId(field.getName());
                continue;
            }
            builder.addValue(field.getName());
        }
    }
}
