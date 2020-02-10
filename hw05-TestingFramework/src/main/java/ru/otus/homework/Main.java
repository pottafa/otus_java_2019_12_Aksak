package ru.otus.homework;

import ru.otus.homework.testingFramework.TestLauncher;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        TestLauncher.launchTests(TestClass.class.getName());
        TestLauncher.launchTests(TestClass2.class.getName());
        TestLauncher.launchTests(TestClass3.class.getName());
    }
}
