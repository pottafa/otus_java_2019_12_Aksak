package ru.otus.homework.atm;

public class AtmMemento {
    private final AtmImpl atm;

    AtmMemento(AtmImpl atm) {
        this.atm = new AtmImpl(atm);
    }

    AtmImpl getSavedState() {
        return atm;
    }
}
