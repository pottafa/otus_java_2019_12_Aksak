package ru.otus.homework.atm;

import java.util.Map;

public class GetBalance implements Operation<Integer> {
    private final ATM atm;
    private final Map<Banknote, Integer> cells;

    public GetBalance(ATM atm) {
        this.atm = atm;
        cells = atm.getCells();
    }

    @Override
    public Integer execute() {
        return cells.values().stream().mapToInt(Integer::valueOf).sum();
    }
}
