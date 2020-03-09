package ru.otus.homework.diyGson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DiyGsonTypes {
    private static final Set<String> WRAPPER_TYPES = getWrapperTypes();

    static Type getType(Object object) {
        if (object == null) return Type.NULL;
        var objClass = object.getClass();
        var className = object.getClass().getSimpleName();
        if (WRAPPER_TYPES.contains(className)) return Type.PRIMITIVE;
        else if (objClass.isArray()) return Type.ARRAY;
        else if (objClass == String.class) return Type.STRING;
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

    static Set<String> getWrapperTypes() {
        Set<String> typeSet = new HashSet<>();
        typeSet.add("Boolean");
        typeSet.add("Character");
        typeSet.add("Byte");
        typeSet.add("Short");
        typeSet.add("Integer");
        typeSet.add("Long");
        typeSet.add("Float");
        typeSet.add("Double");
        typeSet.add("Void");
        return typeSet;
    }
}
