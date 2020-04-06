package ru.otus.homework.appcontainer;

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

    private void processConfig(Class<?>... configClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var annotatedMethods = new HashMap<Method, Object>();
        for (Class<?> aClass : configClass) {
            checkConfigClass(aClass);
            parseMethods(annotatedMethods, aClass);
        }
        List<Method> sortedMethods = sortMethods(annotatedMethods);
        process(annotatedMethods, sortedMethods.iterator());
        if (sortedMethods.size() != 0) process(annotatedMethods, sortedMethods.iterator());
    }

    private void process(HashMap<Method, Object> annotatedMethods, Iterator<Method> listIterator) throws IllegalAccessException, InvocationTargetException {
        while (listIterator.hasNext()) {
            var method = listIterator.next();
            List<Object> params = getMethodParams(method);
            if (params == null || !method.isAnnotationPresent(AppComponent.class)) continue;
            var annotationNameValue = method.getAnnotation(AppComponent.class).name();
            Object methodResult = method.invoke(annotatedMethods.get(method), params.toArray());
            appComponents.add(methodResult);
            appComponentsByName.put(annotationNameValue, methodResult);
            listIterator.remove();
        }
    }

    private List<Method> sortMethods(Map<Method, Object> annotatedMethods) {
        return annotatedMethods.keySet().stream()
                .sorted(Comparator.comparingInt(Method::getParameterCount))
                .collect(Collectors.toList());
    }

    private List<Object> getMethodParams(Method method) {
        List<Object> params = new ArrayList<>();
        for (Class<?> clazz : method.getParameterTypes())
            for (Object obj : appComponents)
                if (clazz.isAssignableFrom(obj.getClass())) {
                    params.add(obj);
                    break;
                }
        if (params.size() != method.getParameterCount()) return null;
        return params;
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
