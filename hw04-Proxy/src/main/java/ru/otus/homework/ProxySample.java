package ru.otus.homework;

import ru.otus.homework.logging.Log;
import ru.otus.homework.logging.Proxibale;
import ru.otus.homework.logging.TestLogging;
import ru.otus.homework.logging.TestLoggingInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class ProxySample {
    static <T extends Proxibale> T createClass(T someInterface) throws ClassNotFoundException {
        ArrayList<String> methodsToInvoke = new ArrayList<>();
        Class cl = Class.forName(someInterface.getClass().getName());
        for (Method method : cl.getMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation.toString().equals("@ru.otus.homework.logging.Log()")) {
                    methodsToInvoke.add(method.getName());
                }
            }
        }
        if (methodsToInvoke.size() == 0) return someInterface;
        for (Class interfaces : cl.getInterfaces()) {
            if (Proxibale.class.isAssignableFrom(interfaces)) {
                return (T) Proxy.newProxyInstance(ProxySample.class.getClassLoader(), new Class<?>[]{interfaces}, (proxy, method, args) -> {
                    if (methodsToInvoke.contains(method.getName())) {
                        System.out.println("executed method:" + method.getName() + ", param: " + args[0]);
                    }
                    return method.invoke(someInterface, args);
                });
            }
        }
        return someInterface;
    }
}