package ru.otus.homework;

import java.util.*;

public class Person {
    public int age = 30;
    public String name = "John Doe";
    public ArrayList<Integer> amountOfDogs = new ArrayList<>();
    public Set<Integer> children = new HashSet<>();

    public List<Person> friends = new ArrayList<>();

    @Override
    public String toString() {
        return "Person{" +
                "size=" + age +
                ", name='" + name + '\'' +
                ", arrayList=" + amountOfDogs +
                ", set=" + children +
                ", list=" + friends +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(amountOfDogs, person.amountOfDogs) &&
                Objects.equals(children, person.children) &&
                Objects.equals(friends, person.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, amountOfDogs, children, friends);
    }
}
