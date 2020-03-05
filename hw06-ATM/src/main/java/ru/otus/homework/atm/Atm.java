package ru.otus.homework.atm;

import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.operations.AtmOperation;

import java.util.Map;

public interface Atm {

     <T> T execute(AtmOperation<?> atmOperation) throws AtmException ;

     AtmMemento createCopy();

     void restoreInitState() ;

     Map<Banknote, Integer> getCells();
}
