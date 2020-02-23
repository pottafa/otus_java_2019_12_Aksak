package ru.otus.homework.atm;

import java.util.HashMap;
import java.util.Map;

public class ATM {
    private final Map<Banknote, Integer> cells = new HashMap<>();
    public Operation<?> operation;

    public void addOperation(Operation<?> operation) {
        this.operation = operation;
    }

    public <T> T execute() throws AtmException {
        return (T) this.operation.execute();
    }


    Map<Banknote, Integer> getCells() {
        return cells;
    }
}
