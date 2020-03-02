package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.AtmImpl;
import ru.otus.homework.atm.exceptions.AtmException;

public interface AtmOperation<T> {
    T execute(AtmImpl atmImpl) throws AtmException;
}
