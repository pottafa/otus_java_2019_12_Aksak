package ru.otus.homework.atm;

import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.operations.DepartmentOperation;
import ru.otus.homework.atm.operations.GetBalance;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final List<AtmImpl> atmsListeners = new ArrayList<>();

    public void addListener(AtmImpl atmImplListener) {
        atmsListeners.add(atmImplListener);
    }

    public void removeListener(AtmImpl atmImplListener) {
        atmsListeners.remove(atmImplListener);
    }

    public AtmBuilder getAtmBuilder() {
        return new AtmBuilder(this);
    }

    public void event(DepartmentOperation atmOperation) {
        atmsListeners.forEach(listener -> {
            try {
                listener.execute(atmOperation);
            } catch (AtmException e) {
                e.printStackTrace();
            }
        });
    }

    public int getAllAtmBalance() {
        return atmsListeners.stream().mapToInt(atm -> {
            try {
                return atm.execute(new GetBalance());
            } catch (AtmException e) {
                e.printStackTrace();
            }
            return 0;
        }).sum();
    }
}


