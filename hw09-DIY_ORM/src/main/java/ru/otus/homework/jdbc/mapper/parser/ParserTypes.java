package ru.otus.homework.jdbc.mapper.parser;

import java.util.HashSet;
import java.util.Set;

class ParserTypes {
    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    static Type getType(Object object) {
        var objClass = object.getClass();
        if (WRAPPER_TYPES.contains(objClass) || objClass == String.class) return Type.FIELD;
        else return Type.OBJECT;
    }

    enum Type {
        FIELD,
        OBJECT
    }

    static Set<Class<?>> getWrapperTypes() {
        Set<Class<?>> typeSet = new HashSet<>();
        typeSet.add(Boolean.class);
        typeSet.add(Byte.class);
        typeSet.add(Short.class);
        typeSet.add(Integer.class);
        typeSet.add(Long.class);
        typeSet.add(Float.class);
        typeSet.add(Double.class);
        typeSet.add(Void.class);
        typeSet.add(Character.class);
        return typeSet;
    }
}
