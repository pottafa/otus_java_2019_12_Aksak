package ru.otus.homework.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import ru.otus.homework.appcontainer.api.AppComponent;
import ru.otus.homework.appcontainer.api.AppComponentsContainer;
import ru.otus.homework.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) throws AppContainerException {
        try {
            processConfig(initialConfigClasses);
        } catch (Exception e) {
            throw new AppContainerException(e);
        }
    }

    public AppComponentsContainerImpl(String packagePath) throws AppContainerException {
        Reflections reflections = new Reflections(packagePath,
                new TypeAnnotationsScanner());
        Set<Class<?>> packageClasses = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class, true);
        Class<?>[] configClasses =  new Class<?>[packageClasses.size()];
        configClasses = packageClasses.toArray(configClasses);
        try {
            processConfig(configClasses);
        } catch (Exception ex) {
            throw new AppContainerException(ex);
        }

    }

    private void processConfig(Class<?>... configClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, AppContainerException {
        var annotatedMethods = new HashMap<Method, Object>();
        for (Class<?> aClass : configClass) {
            checkConfigClass(aClass);
            parseMethods(annotatedMethods, aClass);
        }
        List<Method> sortedMethods = sortMethods(annotatedMethods);
        process(annotatedMethods, sortedMethods);
    }

    private List<Method> sortMethods(HashMap<Method, Object> annotatedMethods) {
        return   annotatedMethods.keySet().stream()
                  .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                  .collect(Collectors.toList());
    }

    private void process(HashMap<Method, Object> annotatedMethods, List<Method> methods) throws IllegalAccessException, InvocationTargetException, AppContainerException {
       for(Method methodToProcess: methods) {
           if (!methodToProcess.isAnnotationPresent(AppComponent.class)) continue;
           List<Object> params = getMethodParams(methodToProcess);
           var annotationNameValue = methodToProcess.getAnnotation(AppComponent.class).name();
           Object methodResult = methodToProcess.invoke(annotatedMethods.get(methodToProcess), params.toArray());
           appComponents.add(methodResult);
           appComponentsByName.put(annotationNameValue, methodResult);
       }
    }

    private List<Object> getMethodParams(Method method) throws AppContainerException {
        List<Object> params = new ArrayList<>();
        for (Class<?> clazz : method.getParameterTypes())
            for (Object obj : appComponents)
                if (clazz.isAssignableFrom(obj.getClass())) {
                    params.add(obj);
                    break;
                }
        if (params.size() == method.getParameterCount()) return params;
        throw new AppContainerException(String.format("Wrong order value in method %s", method.getName()));
    }

    private void parseMethods(Map<Method, Object> annotatedMethods, Class<?> configClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var configInstance = configClass.getDeclaredConstructor().newInstance();
        for (Method method : configClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AppComponent.class))
                annotatedMethods.put(method, configInstance);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object obj : appComponents) {
            if (componentClass.isAssignableFrom(obj.getClass())) return (C) obj;
        }
        throw new NoSuchElementException(String.format("There is no app component by this class: %s", componentClass.getName()));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        for (String name : appComponentsByName.keySet()) {
            if (name.equals(componentName)) return (C) appComponentsByName.get(name);
        }
        throw new NoSuchElementException(String.format("There is no app component by this name: %s", componentName));
    }
}
