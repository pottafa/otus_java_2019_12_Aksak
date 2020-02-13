package ru.otus.homework;

import ru.otus.homework.logging.*;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        TestLoggingInterface obj1 = new TestLogging();
        TestLoggingInterface myImpl1 = ProxySample.createClass(obj1);
        myImpl1.calculation(6);                                              // @Log
        myImpl1.overloadCalculation(1, 2);                           //  @Log
        myImpl1.overloadCalculation(111);                                    //  no @Log
        myImpl1.calculationWithoutLog(12);                                   //  no @Log

        TestLoggingAnotherInterface obj2 = new AnotherTestLogging();
        TestLoggingAnotherInterface myImpl2 = ProxySample.createClass(obj2);
        myImpl2.anotherCalculation(22);                                      //  @Log
        myImpl2.anotherOverloadCalculation(222);                                    //  @Log
        myImpl2.anotherOverloadCalculation(3, "[Test]", new ArrayList<>()); //  @Log
        myImpl2.anotherCalculationWithoutLog(333);                           //  no @Log
    }
}
