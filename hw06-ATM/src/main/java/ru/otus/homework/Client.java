package ru.otus.homework;

import ru.otus.homework.atm.*;
import ru.otus.homework.atm.GetBalance;
import ru.otus.homework.atm.Operation;
import ru.otus.homework.atm.PutMoney;

import java.util.Arrays;

public class Client {

    public static void main(String[] args) throws AtmException {
        ATM atm = new ATM();
        Operation putMoney = new PutMoney(atm, Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND);
        Operation getBalance = new GetBalance(atm);
        Operation withdrawMoney = new WithdrawMoney(atm, 7000);
        atm.addOperation(getBalance);
        int balanceBef = atm.execute();
        System.out.println(balanceBef);

        atm.addOperation(putMoney);
        atm.execute();

        atm.addOperation(getBalance);
        int balanceAfter = atm.execute();
        System.out.println(balanceAfter);

        atm.addOperation(withdrawMoney);
        Banknote[] money = atm.execute();
        System.out.println(Arrays.toString(money));

        atm.addOperation(getBalance);
        int balanceAfter1 = atm.execute();
        System.out.println(balanceAfter1);

    }
}
