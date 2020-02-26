package ru.otus.homework.atm;

import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.operations.AtmOperation;

import java.util.HashMap;
import java.util.Map;

public class ATM {
    private Map<Banknote, Integer> cells;
    private AtmOperation<?> atmOperation;
    private AtmMemento initialState;
    private final Department atmDepartment;

    public ATM(Department department) {
        atmDepartment = department;
        cells = new HashMap<>();
    }

    public void addOperation(AtmOperation<?> atmOperation) {
        this.atmOperation = atmOperation;
    }

    public <T> T execute() throws AtmException {
        return (T) atmOperation.execute(this, cells);
    }

    AtmMemento createCopy() {
        if (initialState == null) initialState = new AtmMemento();
        return new AtmMemento();
    }

    public void restoreInitState() {
        initialState.restore();
    }

    private class AtmMemento {
        private final Map<Banknote, Integer> cellsCopy;

        AtmMemento() {
            cellsCopy = new HashMap<>(cells);
        }

        void restore() {
            cells = new HashMap<>(cellsCopy);
        }
    }

}


