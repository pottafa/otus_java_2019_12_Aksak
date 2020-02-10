package ru.otus.homework;

import ru.otus.homework.testingFramework.*;

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
        System.out.println("Before method starts");
    }

    @Test
    public void test2() {
        System.out.println("Tests 2 starts");
        Object object = null;
        System.out.println(object.toString());
    }

    public void test3() {
        System.out.println("Tests 3 starts");
        Object object = null;
        System.out.println(object.toString());
    }

    @Test
    public void test4() {
        System.out.println("Tests 4 starts");
        Object object = null;
        System.out.println(object.toString());
    }

    public void tearDown() {
        System.out.println("After method starts");
    }


}
