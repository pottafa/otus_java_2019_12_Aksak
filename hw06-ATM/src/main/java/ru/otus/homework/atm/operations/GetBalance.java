package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.ATM;
import ru.otus.homework.atm.Banknote;

import java.util.Map;

public class GetBalance implements AtmOperation<Integer> {

    @Override
    public Integer execute(ATM atm, Map<Banknote, Integer> cells) {
        return cells.values().stream().mapToInt(Integer::valueOf).sum();
    }
}
