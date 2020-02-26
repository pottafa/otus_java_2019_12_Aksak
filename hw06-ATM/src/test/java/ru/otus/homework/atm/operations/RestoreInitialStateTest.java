package ru.otus.homework.atm.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.ATM;
import ru.otus.homework.atm.Banknote;
import ru.otus.homework.atm.Department;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.exceptions.DepartamentException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestoreInitialStateTest {
    Department department;
    ATM atm1;
    ATM atm2;
    ATM atm3;
    ATM atm4;
    int atm1Balance;
    int atm2Balance;
    int atm3Balance;
    int atm4Balance;

    @BeforeEach
    void setUp() throws DepartamentException, AtmException {
        department = new Department();
        atm1 = department.getAtm();
        atm2 = department.getAtm();
        atm3 = department.getAtm();
        atm4 = department.getAtm();

        AtmOperation getBalance = new GetBalance();
        atm1.addOperation(getBalance);
        atm1Balance = atm1.execute();

        atm2.addOperation(getBalance);
        atm2Balance = atm2.execute();

        atm3.addOperation(getBalance);
        atm3Balance = atm3.execute();

        atm4.addOperation(getBalance);
        atm4Balance = atm4.execute();
    }
@DisplayName("Restoring 4 atm to initial state")
    @Test
    void execute() throws AtmException {
        atm1.addOperation(new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED));
        atm1.execute();

        atm2.addOperation(new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED, Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED));
        atm2.execute();

        atm3.addOperation(new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED, Banknote.FIVE_HUNDRED));
        atm3.execute();

        atm4.addOperation(new WithdrawMoney(atm4Balance));
        atm4.execute();

        department.event(new RestoreInitialState());

        AtmOperation getBalance = new GetBalance();
        atm1.addOperation(getBalance);
        int atm1BalanceAfter = atm1.execute();

        atm2.addOperation(getBalance);
        int atm2BalanceAfter = atm2.execute();

        atm3.addOperation(getBalance);
        int atm3BalanceAfter = atm3.execute();

        atm4.addOperation(getBalance);
        int atm4BalanceAfter = atm4.execute();
int overallBalanceBefore = atm1Balance+atm2Balance+atm3Balance+atm4Balance;
int overallBalanceAfter = atm1BalanceAfter + atm2BalanceAfter + atm3BalanceAfter + atm4BalanceAfter;

        assertEquals(overallBalanceBefore, overallBalanceAfter);

    }
}