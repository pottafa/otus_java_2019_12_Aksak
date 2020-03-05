package ru.otus.homework.atm;

public enum Banknote {
    FIVE_THOUSAND(5000),
    THOUSAND(1000),
    FIVE_HUNDRED(500),
    HUNDRED(100),
    FIFTY(50),
    TEN(10);

    private final int value;

    Banknote(int number) {
        this.value = number;
    }

    public int getValue() {
        return value;
    }
}
