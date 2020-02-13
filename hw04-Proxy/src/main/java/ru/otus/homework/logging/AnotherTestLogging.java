package ru.otus.homework.logging;

import java.util.List;

public class AnotherTestLogging implements TestLoggingAnotherInterface {
    @Log
    public void anotherCalculation(int param) {
    }

    @Override
    @Log
    public void anotherOverloadCalculation(int param) {
    }

    @Log
    @Override
    public void anotherOverloadCalculation(int param, String param2, List<Integer> param3) {

    }

    public void anotherCalculationWithoutLog(int param) {

    }
}
