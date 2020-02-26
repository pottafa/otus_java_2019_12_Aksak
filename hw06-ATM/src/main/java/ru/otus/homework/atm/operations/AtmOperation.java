package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.ATM;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.Banknote;

import java.util.Map;

public interface AtmOperation<T> {
    T execute(ATM atm, Map<Banknote, Integer> cells) throws AtmException;
}
