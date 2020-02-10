package ru.otus.homework.testingFramework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestLauncher {

    public static void launchTests(String className) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Class<?> cl = Class.forName(className);
        Constructor<?> constructor = cl.getConstructor();

        Method before = null;
        Method after = null;
        List<Method> test = new ArrayList<>();
        Method afterAll = null;
        Method beforeAll = null;

        for (Method method : cl.getMethods()) {
            int methodModifiers = method.getModifiers();
            if (method.isAnnotationPresent(Before.class)) {
                before = method;
            } else if (method.isAnnotationPresent(After.class)) {
                after = method;
            } else if (method.isAnnotationPresent(Test.class)) {
                test.add(method);
            } else if (method.isAnnotationPresent(BeforeAll.class) && Modifier.isStatic(methodModifiers)) {
                beforeAll = method;
            } else if (method.isAnnotationPresent(AfterAll.class) && Modifier.isStatic(methodModifiers)) {
                afterAll = method;
            }
        }
        if (test.size() == 0) System.out.println("There is no methods to test");
        else {
            Collections.shuffle(test);
            int testsPassed = 0;
            int testsFailed = 0;
            for (Method testMethod : test) {
                Object objOfClass = constructor.newInstance();
                if (beforeAll != null) beforeAll.invoke(null);
                try {
                    if (before != null) before.invoke(objOfClass);
                    testMethod.invoke(objOfClass);
                    testsPassed += 1;
                    System.out.println(testMethod.getName() + " passed \n");
                } catch (Exception exception) {
                    testsFailed += 1;
                    System.out.println(testMethod.getName() + " failed \n" + exception.getCause() + "\n" + Arrays.toString(exception.getStackTrace()) + "\n");
                } finally {
                    if (after != null) after.invoke(objOfClass);
                }
            }
            if (afterAll != null) afterAll.invoke(null);
            System.out.println("Tests failed: " + testsFailed + ", passed: " + testsPassed + " of " + test.size() + " tests \n");
        }
    }

}
