package ru.otus.homework.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawMoneyTest {

    ATM atm;

    @BeforeEach
    void setUp() throws AtmException {
        atm = new ATM();
        Operation putMoney = new PutMoney(atm, Banknote.FIVE_HUNDRED,Banknote.FIVE_THOUSAND,Banknote.HUNDRED,Banknote.FIFTY,Banknote.THOUSAND,Banknote.FIVE_HUNDRED,Banknote.FIVE_THOUSAND,Banknote.HUNDRED,Banknote.FIFTY,Banknote.THOUSAND);
        atm.addOperation(putMoney);
        atm.execute();
    }
@DisplayName("Withdraw 1100")
    @Test
    void withdrawOneThousandAndOneHundred() throws AtmException {
        Operation withdrawMoney = new WithdrawMoney(atm, 1100);
        atm.addOperation(withdrawMoney);
        Banknote[] expectedBanknotes = new Banknote[] {Banknote.THOUSAND,Banknote.HUNDRED};
        assertArrayEquals(expectedBanknotes, atm.execute());
    }

    @DisplayName("Withdraw everything")
    @Test
    void withdrawEverything() throws AtmException {
        Operation withdrawMoney = new WithdrawMoney(atm, 13300);
        atm.addOperation(withdrawMoney);
        Banknote[] expectedBanknotes = new Banknote[] {Banknote.FIVE_THOUSAND, Banknote.FIVE_THOUSAND, Banknote.THOUSAND, Banknote.THOUSAND, Banknote.FIVE_HUNDRED, Banknote.FIVE_HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.FIFTY, Banknote.FIFTY};
        assertArrayEquals(expectedBanknotes, atm.execute());
    }

    @DisplayName("Withdraw 0")
    @Test
    void withdrawZero() {
        Operation withdrawMoney = new WithdrawMoney(atm, 0);
        atm.addOperation(withdrawMoney);
        assertThrows(AtmException.class, () -> atm.execute());
    }

    @DisplayName("Withdraw incorrect amount of money")
    @Test
    void withdrawIncorrectAmountOfMoney()  {
        Operation withdrawMoney = new WithdrawMoney(atm, 10010);
        atm.addOperation(withdrawMoney);
        assertThrows(AtmException.class, () -> atm.execute());
    }
}