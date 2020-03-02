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

class WithdrawMoneyTest {

    Department department;
    AtmImpl atmImpl;
    int initialBalance;

    @BeforeEach
    void setUp() throws AtmException {
        department = new Department();
        atmImpl = department.getAtmBuilder()
                .fiveTHousand(10)
                .thousand(10)
                .hundred(10)
                .build();
        atmImpl.addOperation(new GetBalance());
        initialBalance = atmImpl.execute();
    }

    @DisplayName("Withdraw 1100")
    @Test
    void withdrawOneThousandAndOneHundred() throws AtmException {
        AtmOperation withdrawMoney = new WithdrawMoney(1100);
        atmImpl.addOperation(withdrawMoney);
        Map<Banknote, Integer> expectedBanknotes = Map.of(Banknote.THOUSAND, 1, Banknote.HUNDRED, 1);
        assertEquals(expectedBanknotes, atmImpl.execute());
    }

    @DisplayName("Withdraw everything")
    @Test
    void withdrawEverything() throws AtmException {
        AtmOperation withdrawMoney = new WithdrawMoney(initialBalance);
        atmImpl.addOperation(withdrawMoney);
        atmImpl.execute();
        atmImpl.addOperation(new GetBalance());
        int balance = atmImpl.execute();
        assertEquals(0, balance);
    }

    @DisplayName("Withdraw 0")
    @Test
    void withdrawZero() {
        AtmOperation withdrawMoney = new WithdrawMoney(0);
        atmImpl.addOperation(withdrawMoney);
        assertThrows(AtmException.class, () -> atmImpl.execute());
    }

    @DisplayName("Withdraw incorrect amount of money")
    @Test
    void withdrawIncorrectAmountOfMoney() {
        AtmOperation withdrawMoney = new WithdrawMoney(10010);
        atmImpl.addOperation(withdrawMoney);
        assertThrows(AtmException.class, () -> atmImpl.execute());
    }
}