package ru.otus.homework.logging;

public class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {
    }

    @Log
    public void calculation2(int param) {
    }

    public void calculationWithoutLog(int param) {

    }
}
