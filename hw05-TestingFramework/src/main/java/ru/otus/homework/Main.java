package ru.otus.homework;

import ru.otus.homework.testingFramework.TestLauncher;


public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        TestLauncher.launchTests(TestClass.class.getName());
        TestLauncher.launchTests(TestClass2.class.getName());
        TestLauncher.launchTests(TestClass3.class.getName());
        TestLauncher.launchTests(TestClass4.class.getName());
    }
}
