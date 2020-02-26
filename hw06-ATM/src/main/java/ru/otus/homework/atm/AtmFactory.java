package ru.otus.homework.atm;

class AtmFactory {
    ATM create(Department department) {
        return new ATM(department);
    }
}
