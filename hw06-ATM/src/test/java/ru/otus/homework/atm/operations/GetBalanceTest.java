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

class GetBalanceTest {
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

    @DisplayName("Balance is zero")
    @Test
    void zeroBalance() throws AtmException {


        atm.addOperation(new WithdrawMoney(initialBalance));
        atm.execute();

        atm.addOperation(new GetBalance());
        int finalBalance = atm.execute();

        assertEquals(0, finalBalance);
    }

    @DisplayName("Ordinary request")
    @Test
    void ordinaryBalance() throws AtmException {
        AtmOperation putMoney = new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED);
        atm.addOperation(putMoney);
        atm.execute();

        AtmOperation getBalance = new GetBalance();
        atm.addOperation(getBalance);
        int balance = atm.execute();

        assertEquals(initialBalance + 7150, balance);
    }

}