package ru.otus.homework.logging;

public interface TestLoggingInterface {
    void calculation(int param);

    void overloadCalculation(int param);

    void overloadCalculation(int param, int param2);

    void calculationWithoutLog(int param);
}