package ru.otus.homework;

import ru.otus.homework.logging.TestLoggingInterface;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        TestLoggingInterface myImpl = ProxySample.createClass();
        myImpl.calculation(6);
    }
}
