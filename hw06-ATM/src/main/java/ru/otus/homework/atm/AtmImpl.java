package ru.otus.homework.atm;

import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.operations.AtmOperation;

import java.util.HashMap;
import java.util.Map;

public class AtmImpl {
    private Map<Banknote, Integer> cells;
    private AtmOperation<?> atmOperation;
    private AtmMemento initialState;

    AtmImpl(Map<Banknote, Integer> money) {
        cells = money;
        initialState = createCopy();
    }

    AtmImpl(AtmImpl origin) {
        cells = new HashMap<>(origin.cells);
    }

    public void addOperation(AtmOperation<?> atmOperation) {
        this.atmOperation = atmOperation;
    }

    public <T> T execute() throws AtmException {
        return (T) atmOperation.execute(this);
    }

    public AtmMemento createCopy() {
        return new AtmMemento(this);
    }

    public void restoreInitState() {
        AtmImpl atmCopy = initialState.getSavedState();
        this.cells = new HashMap<>(atmCopy.cells);
    }

    public Map<Banknote, Integer> getCells() {
        return cells;
    }

}


