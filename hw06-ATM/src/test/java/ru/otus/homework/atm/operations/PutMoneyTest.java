package ru.otus.homework.atm.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.AtmImpl;
import ru.otus.homework.atm.Banknote;
import ru.otus.homework.atm.Department;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.exceptions.DepartamentException;

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
        atmImpl.addOperation(new GetBalance());
        initialBalance = atmImpl.execute();
    }

    @DisplayName("Cheking if the return result is right")
    @Test
    void checkingReturnResultTrue() throws AtmException {
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.THOUSAND, 2, Banknote.HUNDRED, 1, Banknote.FIFTY, 1));
        atmImpl.addOperation(putMoney);
        Boolean result = atmImpl.execute();
        assertTrue(result);
    }

    @DisplayName("No banknotes inserted")
    @Test
    void checkingReturnResultFalse() {
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 0));
        atmImpl.addOperation(putMoney);
        assertThrows(AtmException.class, () -> atmImpl.execute());
    }

    @DisplayName("Compare inserted amount of money with actual result")
    @Test
    void compareMoneyInTheAtmAndInserted() throws AtmException {
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.THOUSAND, 2, Banknote.HUNDRED, 1, Banknote.FIFTY, 1));
        atmImpl.addOperation(putMoney);
        atmImpl.execute();

        AtmOperation getBalance = new GetBalance();
        atmImpl.addOperation(getBalance);
        int balance = atmImpl.execute();

        assertEquals(initialBalance + 7150, balance);
    }

    @DisplayName("Put money 2 times")
    @Test
    void putTwice() throws AtmException {
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.THOUSAND, 2, Banknote.HUNDRED, 1, Banknote.FIFTY, 1));
        atmImpl.addOperation(putMoney);
        atmImpl.execute();

        AtmOperation putMoney2 = new PutMoney(Map.of(Banknote.FIVE_THOUSAND, 1, Banknote.FIVE_HUNDRED, 1));
        atmImpl.addOperation(putMoney2);
        atmImpl.execute();

        AtmOperation getBalance = new GetBalance();
        atmImpl.addOperation(getBalance);
        int balance = atmImpl.execute();

        assertEquals(initialBalance + 12650, balance);
    }
}