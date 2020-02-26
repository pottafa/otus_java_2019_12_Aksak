package ru.otus.homework.atm;

import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.exceptions.DepartamentException;
import ru.otus.homework.atm.operations.PutMoney;

import java.util.ArrayList;
import java.util.List;

class AtmPool {
    private final List<ATM> pool;
    private int current = 0;
    private final Department department;

    AtmPool(int size, AtmFactory factory, Department department) throws AtmException {
        this.department = department;
        pool = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            ATM atm = factory.create(department);
            if (index % 2 == 0) {
                atm.addOperation(new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.THOUSAND, Banknote.THOUSAND, Banknote.THOUSAND, Banknote.THOUSAND, Banknote.THOUSAND, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY));
                atm.execute();
                atm.createCopy();
            } else {
                atm.addOperation(new PutMoney(Banknote.FIVE_HUNDRED, Banknote.FIVE_HUNDRED, Banknote.FIVE_THOUSAND, Banknote.THOUSAND, Banknote.THOUSAND, Banknote.THOUSAND, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.HUNDRED, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY, Banknote.FIFTY));
                atm.execute();
                atm.createCopy();
            }
            pool.add(atm);
        }
    }

    ATM getAtm() throws DepartamentException {
        if (pool.size() == current) {
            throw new DepartamentException("There are no free ATMs");
        }
        department.addListener(pool.get(current));
        return pool.get(current++);
    }
}
