package ru.otus.homework;

import ru.otus.homework.logging.*;

import java.util.ArrayList;
import java.util.Collection;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        TestLoggingInterface obj1 = new TestLogging();
        TestLoggingInterface myImpl1 = ProxySample.createClass(obj1);
        myImpl1.calculation(6);
        myImpl1.calculation2(111);
        myImpl1.calculationWithoutLog(12);

        TestLoggingAnotherInterface obj2 = new AnotherTestLogging();
        TestLoggingAnotherInterface myImpl2 = ProxySample.createClass(obj2);
        myImpl2.anotherCalculation(22);
        myImpl2.anotherCalculation2(222);
        myImpl2.anotherCalculationWithoutLog(333);
    }
}
