package ru.otus.homework.logging;

import java.util.ArrayList;
import java.util.List;

public interface TestLoggingAnotherInterface extends Proxibale {
    void anotherCalculation(int param);

    void anotherOverloadCalculation(int param);

    void anotherOverloadCalculation(int param, String param2, List<Integer> param3);

    void anotherCalculationWithoutLog(int param);
}

