package ru.otus.homework.atm;

import java.util.HashMap;
import java.util.Map;

public class AtmBuilder {
    Map<Banknote, Integer> money = new HashMap<>();
    Department department;

    AtmBuilder(Department department) {
        this.department = department;
    }

    public AtmBuilder fifty(int amount) {
        money.put(Banknote.FIFTY, amount);
        return this;
    }

    public AtmBuilder hundred(int amount) {
        money.put(Banknote.HUNDRED, amount);
        return this;
    }

    public AtmBuilder fiveHundred(int amount) {
        money.put(Banknote.FIVE_HUNDRED, amount);
        return this;
    }

    public AtmBuilder thousand(int amount) {
        money.put(Banknote.THOUSAND, amount);
        return this;
    }

    public AtmBuilder fiveTHousand(int amount) {
        money.put(Banknote.FIVE_THOUSAND, amount);
        return this;
    }

    public AtmImpl build() {
        AtmImpl atmImpl = new AtmImpl(money);
        department.addListener(atmImpl);
        return atmImpl;
    }
}
