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
        int balanceBef = atmImpl.execute(getBalance);
        System.out.println(balanceBef);

        atmImpl.execute(putMoney);

        int balanceAfter = atmImpl.execute(getBalance);
        System.out.println(balanceAfter);

        Map<Banknote, Integer> money = atmImpl.execute(withdrawMoney);
        System.out.println(money);

        int balanceAfter1 = atmImpl.execute(getBalance);
        System.out.println(balanceAfter1);

        department.event(new RestoreInitialState());
        System.out.println(department.getAllAtmBalance());

    }
}
