package ru.otus.homework.diyGson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.Person;

import java.lang.reflect.Type;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DiyGsonTestForObjects {
    DiyGson diyGson;
    Gson realGson;

    @BeforeEach
    void setUp() {
        diyGson = new DiyGson();
        realGson = new Gson();
    }

    @DisplayName("Array with primitives type to json")
    @Test
    void primitiveArrayToJson() throws IllegalAccessException {
        int[] array = {2, 3, 4, 5, 6};
        String json = diyGson.toJson(array);
        int[] deserializedArray = realGson.fromJson(json, int[].class);
        assertArrayEquals(array, deserializedArray);
    }

    @DisplayName("Array with objects to json ")
    @Test
    void objectArrayToJson() throws IllegalAccessException {
        Person[] array = {new Person(), new Person()};
        String json = diyGson.toJson(array);
        Person[] deserializedArray = realGson.fromJson(json, Person[].class);
        assertArrayEquals(array, deserializedArray);
    }

    @DisplayName("List to json ")
    @Test
    void listToJson() throws IllegalAccessException {
        List<String> list = new ArrayList<>();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        list.add("First");
        list.add("Second");
        String json = diyGson.toJson(list);
        List<String> deserializedList = realGson.fromJson(json, listType);
        assertEquals(list, deserializedList);
    }

    @DisplayName("Set to json ")
    @Test
    void setToJson() throws IllegalAccessException {
        Set<String> set = new HashSet<>();
        Type listType = new TypeToken<Set<String>>() {
        }.getType();
        set.add("First");
        set.add("Second");
        String json = diyGson.toJson(set);
        Set<String> deserializedSet = realGson.fromJson(json, listType);
        assertEquals(set, deserializedSet);
    }

    @DisplayName("Object to json ")
    @Test
    void objectToJson() throws IllegalAccessException {
        Person person = new Person();
        String json = diyGson.toJson(person);
        Person deserializedPerson = realGson.fromJson(json, Person.class);
        assertEquals(person, deserializedPerson);
    }

    @DisplayName("Object is null")
    @Test
    void nullObjectToJson() throws IllegalAccessException {
        Person person = null;
        String json = diyGson.toJson(person);
        Person deserializedPerson = realGson.fromJson(json, Person.class);
        assertEquals(person, deserializedPerson);
    }

    @DisplayName("Object with null fields")
    @Test
    void objectWithNullFieldsToJson() throws IllegalAccessException {
        Person person = new Person();
        person.amountOfDogs = null;
        person.name = null;
        String json = diyGson.toJson(person);
        Person deserializedPerson = realGson.fromJson(json, Person.class);
        assertNotEquals(deserializedPerson.amountOfDogs, null);

    }

    @DisplayName("List with nulls")
    @Test
    void listWithNullObjectToJson() throws IllegalAccessException {
        List<Person> list = new ArrayList<>();
        Type listType = new TypeToken<List<Person>>() {
        }.getType();
        list.add(null);
        list.add(null);
        String json = diyGson.toJson(list);
        List<Person> deserializedList = realGson.fromJson(json, listType);
        assertEquals(list, deserializedList);
    }

    @DisplayName("String = \"\"")
    @Test
    void empltyStringToJson() throws IllegalAccessException {
        String string = "";
        String json = diyGson.toJson(string);
        String deserializedString = realGson.fromJson(json, String.class);
        assertEquals(string, deserializedString);
    }

    @DisplayName("Not filled array")
    @Test
    void notFilledArrayToJson() throws IllegalAccessException {
        Person[] personArray = new Person[20];
        String json = diyGson.toJson(personArray);
        Person[] deserializedArray = realGson.fromJson(json, Person[].class);
        assertArrayEquals(personArray, deserializedArray);
    }
}