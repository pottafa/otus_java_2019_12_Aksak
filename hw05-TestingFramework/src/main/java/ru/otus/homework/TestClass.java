package ru.otus.homework;

import ru.otus.homework.testingFramework.annotations.*;

public class TestClass {
  @BeforeAll
  public static void globalSetUp() {
    System.out.println("BeforeAll method starts");
    //   throw new IndexOutOfBoundsException();
  }

  @Before
  public void setUp() {
    System.out.println("Before method starts");
    //   throw new IndexOutOfBoundsException();
  }

  @Test
  public void test1() {
    System.out.println("Test1 starts");
  }

  @Test
  public void test2() {
    System.out.println("Tests 2 starts");
    Object object = null;
    //      System.out.println(object.toString());
  }

  @After
  public void tearDown() {
    System.out.println("After method starts");
    //  throw new IndexOutOfBoundsException();
  }

  @AfterAll
  public static void globalTearDown() {
    System.out.println("AfterAll method starts");
    //  throw new UnsupportedOperationException();
  }

}
