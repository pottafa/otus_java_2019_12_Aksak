package ru.otus.homework.testingFramework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


class TestsHandler {
  private Method beforeAll = null;
  private Method before = null;
  private List<Method> tests = new ArrayList<>();
  private Method after = null;
  private Method afterAll = null;

  void setAfterAll(Method afterAll) throws TestingException {
    if (this.afterAll != null) throw new TestingException("two or more AfterAll methods detected");
    this.afterAll = afterAll;
  }

  void setBeforeAll(Method beforeAll) throws TestingException {
    if (this.beforeAll != null) throw new TestingException("two or more BeforeAll methods detected");
    this.beforeAll = beforeAll;
  }

  void setBefore(Method before) throws TestingException {
    if (this.before != null) throw new TestingException("two or more Before methods detected");
    this.before = before;
  }

  void setAfter(Method after) throws TestingException {
    if (this.after != null) throw new TestingException("two or more After methods detected");
    this.after = after;
  }

  void addTest(Method test) {
    tests.add(test);
  }

  void execute(Constructor constructor, Map<String, Integer> statistics) throws Exception {
    Collections.shuffle(tests);
    if (tests.size() == 0) throw new TestingException("No methods to test in " + constructor.getName());
    try {
      invokeBeforeAll();
      for (Method test : tests) {
        var classObject = constructor.newInstance();
        try {
          invokeBefore(classObject, before);
          test.invoke(classObject);
        } catch (Exception e) {
          statistics.merge("Tests failed: ", 1, Integer::sum);
          e.getCause().printStackTrace();
          continue;
        } finally {
          invokeAfter(statistics, classObject);
        }
        statistics.merge("Tests passed: ", 1, Integer::sum);
      }
    } catch (Exception e) {
      e.getCause().printStackTrace();
    } finally {
      statistics.put("Total number of tests: ", tests.size());
      invokeAfterAll();
    }
  }

  private void invokeAfter(Map<String, Integer> statistics, Object classObject) {
    try {
      if (after != null) after.invoke(classObject);
    } catch (Exception e) {
      statistics.merge("Tests failed: ", 1, Integer::sum);
      e.getCause().printStackTrace();
    }
  }

  private void invokeBefore(Object classObject, Method before) throws IllegalAccessException, InvocationTargetException {
    if (before != null) before.invoke(classObject);
  }

  private void invokeAfterAll() {
    try {
      if (afterAll != null) afterAll.invoke(null);
    } catch (Exception e) {
      e.getCause().printStackTrace();
    }
  }

  private void invokeBeforeAll() throws IllegalAccessException, InvocationTargetException {
    if (beforeAll != null) beforeAll.invoke(null);
  }
}
