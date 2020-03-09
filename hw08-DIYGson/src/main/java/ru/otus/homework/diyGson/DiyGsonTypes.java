package ru.otus.homework.diyGson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DiyGsonTypes {
    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    static Type getType(Object object) {
        if (object == null) return Type.NULL;
        var objClass = object.getClass();
        if (WRAPPER_TYPES.contains(objClass)) return Type.PRIMITIVE;
        else if (objClass.isArray()) return Type.ARRAY;
        else if (objClass == String.class || objClass == Character.class) return Type.STRING;
        else if (List.class.isAssignableFrom(objClass)) return Type.LIST;
        else if (Set.class.isAssignableFrom(objClass)) return Type.SET;
        else return Type.OBJECT;
    }

    enum Type {
        PRIMITIVE,
        STRING,
        ARRAY,
        LIST,
        SET,
        OBJECT,
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
