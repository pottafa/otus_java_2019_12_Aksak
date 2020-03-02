package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.AtmImpl;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.Banknote;

import java.util.HashMap;
import java.util.Map;

public class WithdrawMoney implements AtmOperation<Map<Banknote, Integer>> {
    private int requestedMoney;
    Map<Banknote, Integer> cells;

    public WithdrawMoney(int requestedMoney) {
        this.requestedMoney = requestedMoney;
    }

    @Override
    public Map<Banknote, Integer> execute(AtmImpl atmImpl) throws AtmException {
        cells = atmImpl.getCells();
        if (requestedMoney <= 0) throw new AtmException("Impossible to give this amount of money: " + requestedMoney);
        int remainingRequestedAmount = requestedMoney;
        Map<Banknote, Integer> result = new HashMap<>();
        for (Banknote banknote : Banknote.values()) {
            if (!cells.containsKey(banknote) || banknote.getValue() > remainingRequestedAmount) continue;
            while (cells.get(banknote) >= 1 && remainingRequestedAmount >= banknote.getValue()) {
                remainingRequestedAmount -= banknote.getValue();
                result.merge(banknote, 1, Integer::sum);
                cells.compute(banknote, (a, b) -> b - 1);
            }
        }
        if (remainingRequestedAmount != 0)
            throw new AtmException("Impossible to give this amount of money: " + requestedMoney);
        return result;
    }
}
