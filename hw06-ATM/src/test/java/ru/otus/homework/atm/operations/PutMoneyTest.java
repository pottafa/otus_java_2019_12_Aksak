package ru.otus.homework.atm.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.ATM;
import ru.otus.homework.atm.Banknote;
import ru.otus.homework.atm.Department;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.exceptions.DepartamentException;

import static org.junit.jupiter.api.Assertions.*;

class PutMoneyTest {
    Department department;
    ATM atm;
    int initialBalance;

    @BeforeEach
    void setUp() throws DepartamentException, AtmException {
        department = new Department();
        atm = department.getAtm();
        atm.addOperation(new GetBalance());
        initialBalance = atm.execute();
    }

    @DisplayName("Cheking if the return result is right")
    @Test
    void checkingReturnResultTrue() throws AtmException {
        AtmOperation putMoney = new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED);
        atm.addOperation(putMoney);
        Boolean result = atm.execute();
        assertTrue(result);
    }

    @DisplayName("No banknotes inserted")
    @Test
    void checkingReturnResultFalse() {
        AtmOperation putMoney = new PutMoney();
        atm.addOperation(putMoney);
        assertThrows(AtmException.class, () -> atm.execute());
    }

    @DisplayName("Compare inserted amount of money with actual result")
    @Test
    void compareMoneyInTheAtmAndInserted() throws AtmException {
        AtmOperation putMoney = new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED);
        atm.addOperation(putMoney);
        atm.execute();

        AtmOperation getBalance = new GetBalance();
        atm.addOperation(getBalance);
        int balance = atm.execute();

        assertEquals(initialBalance + 7150, balance);
    }

    @DisplayName("Put money 2 times")
    @Test
    void putTwice() throws AtmException {
        AtmOperation putMoney = new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED);
        atm.addOperation(putMoney);
        atm.execute();

        AtmOperation putMoney2 = new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND);
        atm.addOperation(putMoney2);
        atm.execute();

        AtmOperation getBalance = new GetBalance();
        atm.addOperation(getBalance);
        int balance = atm.execute();

        assertEquals(initialBalance + 12650, balance);
    }
}