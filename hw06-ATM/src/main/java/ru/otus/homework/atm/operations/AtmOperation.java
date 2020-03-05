package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.Atm;
import ru.otus.homework.atm.exceptions.AtmException;

public interface AtmOperation<T> {
    T execute(Atm atmImpl) throws AtmException;
}
