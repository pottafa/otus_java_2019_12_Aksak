package ru.otus.homework;

import ru.otus.homework.logging.TestLogging;
import ru.otus.homework.logging.TestLoggingInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxySample {
    static TestLoggingInterface createClass() throws ClassNotFoundException, NoSuchMethodException {
        InvocationHandler handler = new myInvocationHandler(new TestLogging());
        Class cl = Class.forName("ru.otus.homework.logging.TestLogging");
        Method method = cl.getMethod("calculation", int.class);
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (annotation.toString().equals("@ru.otus.homework.logging.Log()")) {
                return (TestLoggingInterface) Proxy.newProxyInstance(ProxySample.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
            }
        }
        return new TestLogging();
    }

    static class myInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;

        myInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("executed method:" + method.getName() + ", param: " + args[0]);
            return method.invoke(myClass, args);
        }
    }
}
