package ru.otus.homework.diyGson;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static ru.otus.homework.diyGson.DiyGsonTypes.getType;

public class DiyGson {
    private final static char OBJECT_START = '{';
    private final static char OBJECT_END = '}';
    private final static char ARRAY_START = '[';
    private final static char ARRAY_END = ']';
    private final static char ELEMENT_SEPARATOR = ',';

    public String toJson(Object object) throws IllegalAccessException {
        DiyGsonBuilder builder = new DiyGsonBuilder();
        if (object == null) return builder.add(null).build();
        parseObject(builder, object);
        return builder.build();
    }

    private void parseObject(DiyGsonBuilder builder, Object objectToParse) throws IllegalAccessException {
        switch (getType(objectToParse)) {
            case OBJECT: {
                builder.add(OBJECT_START);
                var declaredFields = objectToParse.getClass().getDeclaredFields();
                for (int i = 0; i < declaredFields.length; i++) {
                    var field = declaredFields[i];
                    field.setAccessible(true);
                    if (field.get(objectToParse) == null) continue;
                    if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL && (field.getModifiers() & Modifier.STATIC) == Modifier.STATIC)
                        continue;
                    builder.addWithQuotes(field.getName()).add(":");
                    parseObject(builder, field.get(objectToParse));
                    if (i != declaredFields.length - 1) builder.add(ELEMENT_SEPARATOR);
                }
                builder.add(OBJECT_END);
                break;
            }
            case STRING: {
                builder.addWithQuotes(objectToParse.toString());
                break;
            }
            case PRIMITIVE: {
                builder.add(objectToParse.toString());
                break;
            }
            case COLLECTION: {
                parseObject(builder, ((Collection) objectToParse).toArray());
                break;
            }
            case ARRAY: {
                var length = Array.getLength(objectToParse);
                builder.add(ARRAY_START);
                for (int i = 0; i < length; i++) {
                    var object = Array.get(objectToParse, i);
                    parseObject(builder, object);
                    if (i != length - 1) builder.add(ELEMENT_SEPARATOR);
                }
                builder.add(ARRAY_END);
                break;
            }
            case NULL:
                builder.add(null);
        }

    }

}
