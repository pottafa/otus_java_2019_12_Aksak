package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.AtmImpl;

public class RestoreInitialState implements AtmOperation, DepartmentOperation {
    @Override
    public Boolean execute(AtmImpl atmImpl) {
        atmImpl.restoreInitState();
        return true;
    }
}
