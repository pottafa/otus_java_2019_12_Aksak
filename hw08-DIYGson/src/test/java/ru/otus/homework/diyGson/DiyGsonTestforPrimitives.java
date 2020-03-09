package ru.otus.homework.diyGson;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiyGsonTestforPrimitives {
    DiyGson diyGson;
    Gson realGson;

    @BeforeEach
    void setUp() {
        diyGson = new DiyGson();
        realGson = new Gson();
    }

    @DisplayName("int type")
    @Test
    void intToJson() throws IllegalAccessException {
        int number = 23466665;
        String json = diyGson.toJson(number);
        int deserializedNumber = realGson.fromJson(json, int.class);
        assertEquals(number, deserializedNumber);
    }

    @DisplayName("long type")
    @Test
    void longToJson() throws IllegalAccessException {
        long number = 2222222222311111145L;
        String json = diyGson.toJson(number);
        long deserializedNumber = realGson.fromJson(json, long.class);
        assertEquals(number, deserializedNumber);
    }

    @DisplayName("byte type")
    @Test
    void byteToJson() throws IllegalAccessException {
        byte number = 25;
        String json = diyGson.toJson(number);
        byte deserializedNumber = realGson.fromJson(json, byte.class);
        assertEquals(number, deserializedNumber);
    }

    @DisplayName("short type")
    @Test
    void shortToJson() throws IllegalAccessException {
        short number = 2345;
        String json = diyGson.toJson(number);
        short deserializedNumber = realGson.fromJson(json, short.class);
        assertEquals(number, deserializedNumber);
    }

    @DisplayName("float type")
    @Test
    void floatToJson() throws IllegalAccessException {
        float number = 2372222222222222222222222222222222745.5544444F;
        String json = diyGson.toJson(number);
        float deserializedNumber = realGson.fromJson(json, float.class);
        assertEquals(number, deserializedNumber);
    }

    @DisplayName("double type")
    @Test
    void doubleToJson() throws IllegalAccessException {
        double number = 23722222222228888222222222745.5544444;
        String json = diyGson.toJson(number);
        double deserializedNumber = realGson.fromJson(json, double.class);
        assertEquals(number, deserializedNumber);
    }

    @DisplayName("boolean type")
    @Test
    void booleanToJson() throws IllegalAccessException {
        boolean bool = true;
        String json = diyGson.toJson(bool);
        boolean deserializedNumber = realGson.fromJson(json, boolean.class);
        assertEquals(bool, deserializedNumber);
    }

    @DisplayName("char type")
    @Test
    void charToJson() throws IllegalAccessException {
        char number = '/';
        String json = diyGson.toJson(number);
        char deserializedNumber = realGson.fromJson(json, char.class);
        assertEquals(number, deserializedNumber);
    }
}
