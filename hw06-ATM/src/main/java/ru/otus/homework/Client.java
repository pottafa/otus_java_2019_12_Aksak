package ru.otus.homework;

import ru.otus.homework.atm.*;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.exceptions.DepartamentException;
import ru.otus.homework.atm.operations.GetBalance;
import ru.otus.homework.atm.operations.AtmOperation;
import ru.otus.homework.atm.operations.PutMoney;
import ru.otus.homework.atm.Department;
import ru.otus.homework.atm.operations.RestoreInitialState;
import ru.otus.homework.atm.operations.WithdrawMoney;

import java.util.Arrays;

public class Client {

    public static void main(String[] args) throws AtmException, DepartamentException {
        Department department = new Department();
        ATM atm = department.getAtm();
        ATM atm2 = department.getAtm();
        AtmOperation putMoney = new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND, Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.HUNDRED, Banknote.FIFTY, Banknote.THOUSAND);
        AtmOperation getBalance = new GetBalance();
        AtmOperation withdrawMoney = new WithdrawMoney(7000);
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

        department.event(new RestoreInitialState());
        System.out.println(department.getAllAtmBalance());

    }
}
