package ru.otus.homework.logging;

public class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {
    }

    @Log
    public void overloadCalculation(int param, int param2) {

    }

    public void overloadCalculation(int param) {
    }

    public void calculationWithoutLog(int param) {

    }
}
