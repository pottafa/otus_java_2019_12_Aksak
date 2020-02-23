package ru.otus.homework.atm;

import java.util.Map;

public class PutMoney implements Operation {
    private final ATM atm;
    private Banknote[] banknotes;
    private final Map<Banknote, Integer> cells;

    public PutMoney(ATM atm, Banknote... banknotes) {
        this.banknotes = banknotes;
        this.atm = atm;
        cells = atm.getCells();
    }

    @Override
    public Boolean execute() throws AtmException {
        if (banknotes.length == 0) throw new AtmException("Sorry, but you have not inserted a single bill");
        for (Banknote banknote : banknotes) cells.merge(banknote, banknote.getValue(), Integer::sum);
        return true;
    }
}
