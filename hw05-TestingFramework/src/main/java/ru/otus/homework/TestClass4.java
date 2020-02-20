package ru.otus.homework;

import ru.otus.homework.testingFramework.annotations.*;

public class TestClass4 {
    @BeforeAll
    public static void globalSetOup() {
        System.out.println("BeforeAll method starts");
    }

    @Before
    public void setUp() {
        System.out.println("Before method starts");
    }

    @Before
    public void setUp1() {
        System.out.println("Before method starts");
    }

    @Test
    public void test1() {
        System.out.println("Test1 starts");
    }

    @After
    public void tearDown() {
        System.out.println("After method starts");
    }

    @AfterAll
    public static void globalTearDown() {
        System.out.println("AfterAll method starts");
    }

}
