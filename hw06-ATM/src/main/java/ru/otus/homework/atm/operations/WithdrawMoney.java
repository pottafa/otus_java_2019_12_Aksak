package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.ATM;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.Banknote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WithdrawMoney implements AtmOperation<Banknote[]> {
    private int requestedMoney;

    public WithdrawMoney(int requestedMoney) {
        this.requestedMoney = requestedMoney;
    }

    @Override
    public Banknote[] execute(ATM atm, Map<Banknote, Integer> cells) throws AtmException {
        if (requestedMoney <= 0) throw new AtmException("Impossible to give this amount of money: " + requestedMoney);
        int remainingRequestedAmount = requestedMoney;
        List<Banknote> result = new ArrayList<>();
        for (Banknote banknote : Banknote.values()) {
            if (!cells.containsKey(banknote) || banknote.getValue() > remainingRequestedAmount) continue;
            while (cells.get(banknote) >= banknote.getValue() && remainingRequestedAmount >= banknote.getValue()) {
                remainingRequestedAmount -= banknote.getValue();
                result.add(banknote);
                cells.compute(banknote, (a, b) -> b - banknote.getValue());
            }
        }
        if (remainingRequestedAmount != 0)
            throw new AtmException("Impossible to give this amount of money: " + requestedMoney);
        return result.toArray(new Banknote[0]);
    }
}
