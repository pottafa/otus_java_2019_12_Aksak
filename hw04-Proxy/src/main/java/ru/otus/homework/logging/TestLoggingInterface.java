package ru.otus.homework.logging;

public interface TestLoggingInterface extends Proxibale {
    void calculation(int param);

    void calculation2(int param);

    void calculationWithoutLog(int param);
}