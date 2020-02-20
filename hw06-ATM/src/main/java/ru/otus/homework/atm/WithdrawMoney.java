package ru.otus.homework.atm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WithdrawMoney implements Operation<Banknote[]> {
    private final ATM atm;
    private int requestedMoney;
    private final Map<Banknote, Integer> cells;

    public WithdrawMoney(ATM atm, int requestedMoney) {
        this.atm = atm;
        this.requestedMoney = requestedMoney;
        cells = this.atm.getCells();
    }

    @Override
    public Banknote[] execute() throws AtmException {
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
