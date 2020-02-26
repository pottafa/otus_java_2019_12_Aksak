package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.ATM;

import java.util.Map;

public class RestoreInitialState implements AtmOperation, DepartmentOperation {
    @Override
    public Boolean execute(ATM atm, Map cells) {
        atm.restoreInitState();
        return true;
    }
}
