package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.AtmImpl;
import ru.otus.homework.atm.Banknote;

import java.util.Map;

public class GetBalance implements AtmOperation<Integer> {
    private Map<Banknote, Integer> cells;

    @Override
    public Integer execute(AtmImpl atmImpl) {
        cells = atmImpl.getCells();
        return cells.keySet().stream().mapToInt(a -> cells.get(a) * a.getValue()).sum();
    }
}
