package ru.otus.homework.atm.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.AtmImpl;
import ru.otus.homework.atm.Banknote;
import ru.otus.homework.atm.Department;
import ru.otus.homework.atm.exceptions.AtmException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GetBalanceTest {
    Department department;
    AtmImpl atmImpl;
    int initialBalance;

    @BeforeEach
    void setUp() throws AtmException {
        department = new Department();
        atmImpl = department.getAtmBuilder()
                .fiveTHousand(22)
                .thousand(111)
                .build();
        initialBalance = atmImpl.execute(new GetBalance());
    }

    @DisplayName("Balance is zero")
    @Test
    void zeroBalance() throws AtmException {
        atmImpl.execute(new WithdrawMoney(initialBalance));
        int finalBalance = atmImpl.execute(new GetBalance());
        assertEquals(0, finalBalance);
    }

    @DisplayName("Ordinary request")
    @Test
    void ordinaryBalance() throws AtmException {
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.THOUSAND, 2, Banknote.HUNDRED, 1, Banknote.FIFTY, 1));
        atmImpl.execute(putMoney);
        AtmOperation getBalance = new GetBalance();
        int balance = atmImpl.execute(getBalance);
        assertEquals(initialBalance + 7150, balance);
    }

}