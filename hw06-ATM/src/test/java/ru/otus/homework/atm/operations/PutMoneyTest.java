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

class PutMoneyTest {
    Department department;
    AtmImpl atmImpl;
    int initialBalance;

    @BeforeEach
    void setUp() throws AtmException {
        department = new Department();
        atmImpl = department.getAtmBuilder().build();
        initialBalance = atmImpl.execute(new GetBalance());
    }

    @DisplayName("Cheking if the return result is right")
    @Test
    void checkingReturnResultTrue() throws AtmException {
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.THOUSAND, 2, Banknote.HUNDRED, 1, Banknote.FIFTY, 1));
        Boolean result = atmImpl.execute(putMoney);
        assertTrue(result);
    }

    @DisplayName("No banknotes inserted")
    @Test
    void checkingReturnResultFalse() {
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 0));
        assertThrows(AtmException.class, () -> atmImpl.execute(putMoney));
    }

    @DisplayName("Compare inserted amount of money with actual result")
    @Test
    void compareMoneyInTheAtmAndInserted() throws AtmException {
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.THOUSAND, 2, Banknote.HUNDRED, 1, Banknote.FIFTY, 1));
        atmImpl.execute(putMoney);
        AtmOperation getBalance = new GetBalance();
        int balance = atmImpl.execute(getBalance);
        assertEquals(initialBalance + 7150, balance);
    }

    @DisplayName("Put money 2 times")
    @Test
    void putTwice() throws AtmException {
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.THOUSAND, 2, Banknote.HUNDRED, 1, Banknote.FIFTY, 1));
        atmImpl.execute(putMoney);
        AtmOperation putMoney2 = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.FIVE_HUNDRED, 1));
        atmImpl.execute(putMoney2);
        AtmOperation getBalance = new GetBalance();
        int balance = atmImpl.execute(getBalance);
        assertEquals(initialBalance + 12650, balance);
    }
}