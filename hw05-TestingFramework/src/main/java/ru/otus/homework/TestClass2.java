package ru.otus.homework;

import ru.otus.homework.testingFramework.annotations.AfterAll;
import ru.otus.homework.testingFramework.annotations.Before;
import ru.otus.homework.testingFramework.annotations.Test;

public class TestClass2 {

    public static void globalSetOup() {
        System.out.println("BeforeAll method starts");
    }

    @Test
    public void test1() {
        System.out.println("Test1 starts");
    }

    @AfterAll
    public static void globalTearDown() {
        System.out.println("AfterAll method starts");
    }

    @Before
    public void setUp() {

    }

    @Test
    public void test2() {
        throw new ArrayIndexOutOfBoundsException();
    }

    public void test3() {
        throw new RuntimeException();
    }

    @Test
    public void test4() {
        throw new UnsupportedOperationException();
    }

    public void tearDown() {
        System.out.println("After method starts");
    }


}
