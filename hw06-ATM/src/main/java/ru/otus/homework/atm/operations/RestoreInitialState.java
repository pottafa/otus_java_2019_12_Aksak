package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.Atm;

public class RestoreInitialState implements AtmOperation, DepartmentOperation {
    @Override
    public Boolean execute(Atm atmImpl) {
        atmImpl.restoreInitState();
        return true;
    }
}
