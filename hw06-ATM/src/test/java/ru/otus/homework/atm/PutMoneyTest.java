package ru.otus.homework.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PutMoneyTest {
    ATM atm;

    @BeforeEach
    void setUp() {
        atm = new ATM();
    }

@DisplayName("Cheking if the return result is right")
    @Test
    void checkingReturnResultTrue() throws AtmException {
        Operation putMoney = new PutMoney(atm, Banknote.FIVE_HUNDRED,Banknote.FIVE_THOUSAND,Banknote.HUNDRED,Banknote.FIFTY,Banknote.THOUSAND,Banknote.FIVE_HUNDRED);
        atm.addOperation(putMoney);
        Boolean result = atm.execute();
        assertTrue(result);
    }

    @DisplayName("No banknotes inserted")
    @Test
    void checkingReturnResultFalse() {
        Operation putMoney = new PutMoney(atm);
        atm.addOperation(putMoney);
       assertThrows(AtmException.class, () -> atm.execute());
    }

    @DisplayName("Compare inserted amount of money with actual result")
    @Test
    void compareMoneyInTheAtmAndInserted() throws AtmException {
        Operation putMoney = new PutMoney(atm, Banknote.FIVE_HUNDRED,Banknote.FIVE_THOUSAND,Banknote.HUNDRED,Banknote.FIFTY,Banknote.THOUSAND,Banknote.FIVE_HUNDRED);
        atm.addOperation(putMoney);
        atm.execute();

        Operation getBalance = new GetBalance(atm);
        atm.addOperation(getBalance);
        int balance = atm.execute();

        assertEquals(7150,balance);
    }

    @DisplayName("Put money 2 times")
    @Test
    void putTwice() throws AtmException {
        Operation putMoney = new PutMoney(atm, Banknote.FIVE_HUNDRED,Banknote.FIVE_THOUSAND,Banknote.HUNDRED,Banknote.FIFTY,Banknote.THOUSAND,Banknote.FIVE_HUNDRED);
        atm.addOperation(putMoney);
        atm.execute();

        Operation putMoney2 = new PutMoney(atm, Banknote.FIVE_HUNDRED,Banknote.FIVE_THOUSAND);
        atm.addOperation(putMoney2);
        atm.execute();

        Operation getBalance = new GetBalance(atm);
        atm.addOperation(getBalance);
        int balance = atm.execute();

        assertEquals(12650,balance);
    }
}