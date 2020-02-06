package ru.otus.homework;

import ru.otus.homework.logging.Log;
import ru.otus.homework.logging.Proxibale;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class ProxySample {
    static <T extends Proxibale> T createClass(T obj) {
        Map<String, HashSet<String>> methodsToInvoke = new HashMap<>();

        Class cl = obj.getClass();
        for (Method method : cl.getMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation.annotationType().equals(Log.class)) {
                   methodsToInvoke.computeIfAbsent(method.getName(), val ->  new HashSet<>()).add(Arrays.toString(method.getParameters()));
                }
            }
        }
        if (methodsToInvoke.size() == 0) return obj;
        for (Class<?> interfaces : cl.getInterfaces()) {
            if (Proxibale.class.isAssignableFrom(interfaces)) {
                return (T) Proxy.newProxyInstance(ProxySample.class.getClassLoader(), new Class<?>[]{interfaces}, (proxy, method, args) -> {
                    if(methodsToInvoke.containsKey(method.getName()) && methodsToInvoke.get(method.getName()).contains(Arrays.toString(method.getParameters()))) {
                        String argsStr = Arrays.toString(args);
                               System.out.println("executed method:" + method.getName() + ", parameters: " + argsStr.substring(1, argsStr.length() - 1));
                    }
                    return method.invoke(obj, args);
                });
            }
        }
        return obj;
    }
}