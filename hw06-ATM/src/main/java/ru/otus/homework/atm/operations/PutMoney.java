package ru.otus.homework.atm.operations;

import ru.otus.homework.atm.ATM;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.Banknote;

import java.util.Map;

public class PutMoney implements AtmOperation<Boolean> {
    private Banknote[] banknotes;

    public PutMoney(Banknote... banknotes) {
        this.banknotes = banknotes;
    }

    @Override
    public Boolean execute(ATM atm, Map<Banknote, Integer> cells) throws AtmException {
        if (banknotes.length == 0) throw new AtmException("Sorry, but you have not inserted a single bill");
        for (Banknote banknote : banknotes) cells.merge(banknote, banknote.getValue(), Integer::sum);
        return true;
    }
}
