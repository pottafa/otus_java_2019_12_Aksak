package ru.otus.homework;

import ru.otus.homework.logging.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class ProxySample {
    static <T> T createClass(T obj) {
        Set<String> methods = new HashSet<>();

        Class cl = obj.getClass();
        for (Method method : cl.getMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation.annotationType().equals(Log.class)) {
                    String methodSignature = getMethodSignature(method);
                    methods.add(methodSignature);
                }
            }
        }
        if (methods.size() == 0) return obj;
        return (T) Proxy.newProxyInstance(cl.getClassLoader(), cl.getInterfaces(), (proxy, method, args) -> {
            if (methods.contains(getMethodSignature(method))) {
                String argsStr = Arrays.toString(args);
                System.out.println("executed method:" + method.getName() + ", parameters: " + argsStr.substring(1, argsStr.length() - 1));
            }
            return method.invoke(obj, args);
        });
    }

    private static String getMethodSignature(Method method) {
        return method.getName() + Arrays.toString(method.getParameters());
    }
}

