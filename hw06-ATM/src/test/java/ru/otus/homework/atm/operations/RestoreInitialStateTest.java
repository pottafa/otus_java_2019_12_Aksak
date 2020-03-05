package ru.otus.homework.atm.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.AtmImpl;
import ru.otus.homework.atm.Banknote;
import ru.otus.homework.atm.Department;
import ru.otus.homework.atm.exceptions.AtmException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestoreInitialStateTest {
    Department department;
    AtmImpl atmImpl1;
    AtmImpl atmImpl2;
    AtmImpl atmImpl3;
    AtmImpl atmImpl4;
    int atm1Balance;
    int atm2Balance;
    int atm3Balance;
    int atm4Balance;

    @BeforeEach
    void setUp() throws AtmException {
        department = new Department();
        atmImpl1 = department.getAtmBuilder()
                .fifty(1000)
                .hundred(1000)
                .fiveHundred(1000)
                .thousand(1000)
                .fiveTHousand(1000)
                .build();
        atmImpl2 = department.getAtmBuilder()
                .fifty(2000)
                .hundred(2000)
                .fiveHundred(2000)
                .thousand(2000)
                .fiveTHousand(2000)
                .build();
        atmImpl3 = department.getAtmBuilder()
                .hundred(1000)
                .fiveHundred(1000)
                .fiveTHousand(1000)
                .build();
        atmImpl4 = department.getAtmBuilder().build();

        AtmOperation getBalance = new GetBalance();
        atm1Balance = atmImpl1.execute(getBalance);
        atm2Balance = atmImpl2.execute(getBalance);
        atm3Balance = atmImpl3.execute(getBalance);
        atm4Balance = atmImpl4.execute(getBalance);
    }

    @DisplayName("Restoring 4 atm to initial state")
    @Test
    void execute() throws AtmException {
        atmImpl1.execute(new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.THOUSAND, 2, Banknote.HUNDRED, 1, Banknote.FIFTY, 1)));
        atmImpl2.execute(new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 300, Banknote.THOUSAND, 222, Banknote.HUNDRED, 12, Banknote.FIFTY, 1)));
        atmImpl3.execute(new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 11111, Banknote.THOUSAND, 22, Banknote.HUNDRED, 2221, Banknote.FIFTY, 1111)));

        department.event(new RestoreInitialState());

        AtmOperation getBalance = new GetBalance();
        int atm1BalanceAfter = atmImpl1.execute(getBalance);
        int atm2BalanceAfter = atmImpl2.execute(getBalance);
        int atm3BalanceAfter = atmImpl3.execute(getBalance);
        int atm4BalanceAfter = atmImpl4.execute(getBalance);
        int overallBalanceBefore = atm1Balance + atm2Balance + atm3Balance + atm4Balance;
        int overallBalanceAfter = atm1BalanceAfter + atm2BalanceAfter + atm3BalanceAfter + atm4BalanceAfter;

        assertEquals(overallBalanceBefore, overallBalanceAfter);

    }
}