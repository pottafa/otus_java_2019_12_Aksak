package ru.otus.homework.atm;

public interface Operation<T> {
    T execute() throws AtmException;
}
