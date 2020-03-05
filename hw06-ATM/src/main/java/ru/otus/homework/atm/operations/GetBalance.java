package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.Atm;
import ru.otus.homework.atm.Banknote;

import java.util.Map;

public class GetBalance implements AtmOperation<Integer> {

    @Override
    public Integer execute(Atm atmImpl) {
        Map<Banknote, Integer> cells = atmImpl.getCells();
        return cells.keySet().stream().mapToInt(a -> cells.get(a) * a.getValue()).sum();
    }
}
