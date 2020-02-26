package ru.otus.homework.atm;

import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.exceptions.DepartamentException;
import ru.otus.homework.atm.operations.DepartmentOperation;
import ru.otus.homework.atm.operations.GetBalance;
import ru.otus.homework.atm.operations.AtmOperation;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final List<ATM> atmsListeners = new ArrayList<>();
    private final AtmPool pool;

    public Department() throws AtmException {
        pool = new AtmPool(20, new AtmFactory(), this);
    }

    public void addListener(ATM atmListener) {
        atmsListeners.add(atmListener);
    }

    public void removeListener(ATM atmListener) {
        atmsListeners.remove(atmListener);
    }

    public ATM getAtm() throws DepartamentException {
        return pool.getAtm();
    }

    public void event(DepartmentOperation atmOperation) {
        atmsListeners.forEach(atm -> {
            atm.addOperation((AtmOperation) atmOperation);
            try {
                atm.execute();
            } catch (AtmException e) {
                e.printStackTrace();
            }
        });
    }

    public int getAllAtmBalance() {
        return atmsListeners.stream().mapToInt(atm -> {
            atm.addOperation(new GetBalance());
            try {
                return atm.execute();
            } catch (AtmException e) {
                e.printStackTrace();
            }
            return 0;
        }).sum();
    }
}
