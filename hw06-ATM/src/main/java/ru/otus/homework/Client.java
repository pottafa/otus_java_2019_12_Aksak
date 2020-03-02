package ru.otus.homework;

import ru.otus.homework.atm.*;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.operations.GetBalance;
import ru.otus.homework.atm.operations.AtmOperation;
import ru.otus.homework.atm.operations.PutMoney;
import ru.otus.homework.atm.Department;
import ru.otus.homework.atm.operations.RestoreInitialState;
import ru.otus.homework.atm.operations.WithdrawMoney;

import java.util.Map;

public class Client {

    public static void main(String[] args) throws AtmException {
        Department department = new Department();
        AtmImpl atmImpl = department.getAtmBuilder().build();
        AtmImpl atmImpl2 = department.getAtmBuilder().build();
        AtmOperation putMoney = new PutMoney(Map.of(Banknote.HUNDRED, 10, Banknote.FIVE_HUNDRED, 5, Banknote.THOUSAND, 5, Banknote.FIVE_THOUSAND, 40));
        AtmOperation getBalance = new GetBalance();
        AtmOperation withdrawMoney = new WithdrawMoney(7000);
        atmImpl.addOperation(getBalance);
        int balanceBef = atmImpl.execute();
        System.out.println(balanceBef);

        atmImpl.addOperation(putMoney);
        atmImpl.execute();

        atmImpl.addOperation(getBalance);
        int balanceAfter = atmImpl.execute();
        System.out.println(balanceAfter);

        atmImpl.addOperation(withdrawMoney);
        Map<Banknote, Integer> money = atmImpl.execute();
        System.out.println(money);


        atmImpl.addOperation(getBalance);
        int balanceAfter1 = atmImpl.execute();
        System.out.println(balanceAfter1);

        department.event(new RestoreInitialState());
        System.out.println(department.getAllAtmBalance());

    }
}
