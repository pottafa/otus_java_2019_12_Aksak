package ru.otus.homework;

import ru.otus.homework.testingFramework.*;

public class TestClass3 {
    @BeforeAll
    public static void globalSetOup() {
        System.out.println("BeforeAll method starts");
    }

    @Before
    public void setUp() {
        System.out.println("Before method starts");
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
