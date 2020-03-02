package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.AtmImpl;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.Banknote;

import java.util.Map;

public class PutMoney implements AtmOperation<Boolean> {
    private Map<Banknote, Integer> insertedMoney;
    private Map<Banknote, Integer> cells;

    public PutMoney(Map<Banknote, Integer> insertedMoney) {
        this.insertedMoney = insertedMoney;
    }

    @Override
    public Boolean execute(AtmImpl atmImpl) throws AtmException {
        cells = atmImpl.getCells();
        if (insertedMoney.values().stream().noneMatch(banknotes -> banknotes > 0))
            throw new AtmException("Sorry, but you have not inserted a single bill");
        for (Banknote banknote : insertedMoney.keySet())
            cells.merge(banknote, insertedMoney.get(banknote), Integer::sum);
        return true;
    }
}
