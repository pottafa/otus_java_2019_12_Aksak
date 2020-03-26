package ru.otus.homework.jdbc.mapper.parser;

import ru.otus.homework.jdbc.mapper.SqlMapperException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.homework.jdbc.mapper.parser.ParserTypes.getType;

public class JdbcParser {

    List<String> params = new ArrayList<>();
    String idValue ;

    public void parseObject(SqlBuilder builder, Object objectToParse) {
        switch (getType(objectToParse)) {
            case OBJECT: {
                builder.addTableName(objectToParse.getClass().getSimpleName().toLowerCase());
                var declaredFields = objectToParse.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    try {
                    if (field.isAnnotationPresent(Id.class)) {
                        builder.addId(field.getName());
                        idValue = field.get(objectToParse).toString();
                        continue;
                    }
                    builder.addValue(field.getName());
                        if (field.get(objectToParse) == null) {
                            params.add(null);
                            continue;
                        }
                        if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL && (field.getModifiers() & Modifier.STATIC) == Modifier.STATIC)
                            continue;
                        parseObject(builder, field.get(objectToParse));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            }

            case FIELD: {
                params.add(objectToParse.toString());
                break;
            }
            default:
                throw new SqlMapperException("Incorrect data type: " + objectToParse.getClass().getSimpleName());
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

    public List<String> getParams() {
        return params;
    }

    public String getIdValue() {
        return idValue;
    }
}
