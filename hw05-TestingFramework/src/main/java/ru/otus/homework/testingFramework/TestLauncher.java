package ru.otus.homework.testingFramework;

import ru.otus.homework.testingFramework.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class TestLauncher {

    public static Map<String, Integer> launchTests(String className) throws ClassNotFoundException, NoSuchMethodException {
        var cl = Class.forName(className);
        var constructor = cl.getConstructor();
        var statistics = new HashMap<String, Integer>();

        try {
            TestsHandler handler = createHandler(cl.getMethods());
            handler.execute(constructor, statistics);
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (statistics.size() != 0) System.out.println(statistics);
        return statistics;
    }

    private static TestsHandler createHandler(Method[] methodsToSort) throws TestingException {
        var handler = new TestsHandler();
        for (Method method : methodsToSort) {
            int methodModifiers = method.getModifiers();
            if (method.isAnnotationPresent(Before.class)) {
                handler.setBefore(method);
            } else if (method.isAnnotationPresent(After.class)) {
                handler.setAfter(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                handler.addTest(method);
            } else if (method.isAnnotationPresent(BeforeAll.class) && Modifier.isStatic(methodModifiers)) {
                handler.setBeforeAll(method);
            } else if (method.isAnnotationPresent(AfterAll.class) && Modifier.isStatic(methodModifiers)) {
                handler.setAfterAll(method);
            }
        }
        return handler;
    }
}
