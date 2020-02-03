package ru.otus.homework.logging;

public class AnotherTestLogging implements TestLoggingAnotherInterface {
    @Log
    public void anotherCalculation(int param) {
    }

    @Log
    public void anotherCalculation2(int param) {
    }

    public void anotherCalculationWithoutLog(int param) {

    }
}
