package ru.otus.homework.jdbc.mapper.parser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class ParserTypes {
    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    static Type getType(Object object) {
        if (object == null) return Type.NULL;
        var objClass = object.getClass();
        if (WRAPPER_TYPES.contains(objClass)) return Type.PRIMITIVE;
        else if (objClass.isArray()) return Type.ARRAY;
        else if (objClass == String.class || objClass == Character.class) return Type.STRING;
        else if (Collection.class.isAssignableFrom(objClass)) return Type.COLLECTION;
        else return Type.OBJECT;
    }

    enum Type {
        PRIMITIVE,
        STRING,
        ARRAY,
        OBJECT,
        COLLECTION,
        NULL
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
        return typeSet;
    }
}
