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

class WithdrawMoneyTest {

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

    @DisplayName("Withdraw 1100")
    @Test
    void withdrawOneThousandAndOneHundred() throws AtmException {
        AtmOperation withdrawMoney = new WithdrawMoney(1100);
        atm.addOperation(withdrawMoney);
        Banknote[] expectedBanknotes = new Banknote[]{Banknote.THOUSAND, Banknote.HUNDRED};
        assertArrayEquals(expectedBanknotes, atm.execute());
    }

    @DisplayName("Withdraw everything")
    @Test
    void withdrawEverything() throws AtmException {
        AtmOperation withdrawMoney = new WithdrawMoney(initialBalance);
        atm.addOperation(withdrawMoney);
        atm.execute();
        atm.addOperation(new GetBalance());
        int balance = atm.execute();
        assertEquals(0, balance);
    }

    @DisplayName("Withdraw 0")
    @Test
    void withdrawZero() {
        AtmOperation withdrawMoney = new WithdrawMoney(0);
        atm.addOperation(withdrawMoney);
        assertThrows(AtmException.class, () -> atm.execute());
    }

    @DisplayName("Withdraw incorrect amount of money")
    @Test
    void withdrawIncorrectAmountOfMoney() {
        AtmOperation withdrawMoney = new WithdrawMoney(10010);
        atm.addOperation(withdrawMoney);
        assertThrows(AtmException.class, () -> atm.execute());
    }
}