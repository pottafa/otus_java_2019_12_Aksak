package ru.otus.homework.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetBalanceTest {

    ATM atm;

    @BeforeEach
    void setUp() {
        atm = new ATM();
    }

    @DisplayName("Balance is zero")
    @Test
    void zeroBalance() throws AtmException {
        Operation getBalance = new GetBalance(atm);
        atm.addOperation(getBalance);
        int balance = atm.execute();

        assertEquals(0,balance);
    }

    @DisplayName("Ordinary request")
    @Test
    void ordinaryBalance() throws AtmException {
        Operation putMoney = new PutMoney(atm, Banknote.FIVE_HUNDRED,Banknote.FIVE_THOUSAND,Banknote.HUNDRED,Banknote.FIFTY,Banknote.THOUSAND,Banknote.FIVE_HUNDRED);
        atm.addOperation(putMoney);
        atm.execute();

        Operation getBalance = new GetBalance(atm);
        atm.addOperation(getBalance);
        int balance = atm.execute();

        assertEquals(7150,balance);
    }

}