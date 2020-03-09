package ru.otus.homework;

import java.util.*;

public class Person {
    private int age = 30;
    public String name = "John Doe";
    public ArrayList<Integer> amountOfDogs = new ArrayList<>();
    public Set<Integer> children = new HashSet<>();
    private final String RACE;
    private final static int AMOUNT_OF_HEADS = 1;

    public List<Person> friends = new ArrayList<>();

    public Person(String race) {
        RACE = race;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", amountOfDogs=" + amountOfDogs +
                ", children=" + children +
                ", friends=" + friends + "amount of heads" + AMOUNT_OF_HEADS + " race " + RACE +
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
                Objects.equals(RACE, person.RACE) &&
                Objects.equals(friends, person.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, amountOfDogs, children, RACE, friends);
    }
}
