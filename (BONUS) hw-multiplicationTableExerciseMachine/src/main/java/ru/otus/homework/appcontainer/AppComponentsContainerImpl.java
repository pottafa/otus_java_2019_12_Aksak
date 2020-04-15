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
    Class<?>[] configClasses = new Class<?>[packageClasses.size()];
    configClasses = packageClasses.toArray(configClasses);
    try {
      processConfig(configClasses);
    } catch (Exception ex) {
      throw new AppContainerException(ex);
    }

  }

  private void processConfig(Class<?>... configClasses) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, AppContainerException {
    List<Class<?>> sortedConfigs = sortConfigs(configClasses);
    for (Class<?> configClass : sortedConfigs) {
      checkConfigClass(configClass);
      List<Method> configMethods = sortMethods(configClass);
      process(configClass, configMethods);
    }
  }

  private List<Class<?>> sortConfigs(Class<?>[] configClasses) {
    return Arrays.stream(configClasses)
        .filter(clazz -> clazz.isAnnotationPresent(AppComponentsContainerConfig.class))
        .sorted(Comparator.comparingInt(clazz -> clazz.getAnnotation(AppComponentsContainerConfig.class).order()))
        .collect(Collectors.toList());
  }

  private List<Method> sortMethods(Class<?> aClass) {
    return Arrays.stream(aClass.getDeclaredMethods())
        .filter(method -> method.isAnnotationPresent(AppComponent.class))
        .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
        .collect(Collectors.toList());
  }

  private void process(Class<?> configClass, List<Method> methods) throws IllegalAccessException, InvocationTargetException, AppContainerException, NoSuchMethodException, InstantiationException {
    var configInstance = configClass.getDeclaredConstructor().newInstance();
    for (Method methodToProcess : methods) {
      if (!methodToProcess.isAnnotationPresent(AppComponent.class)) continue;
      List<Object> params = getMethodParams(methodToProcess);
      var annotationNameValue = methodToProcess.getAnnotation(AppComponent.class).name();
      Object methodResult = methodToProcess.invoke(configInstance, params.toArray());
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

  private void checkConfigClass(Class<?> configClass) {
    if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
      throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
    }
  }

  @Override
  public <C> C getAppComponent(Class<C> componentClass) {
    return (C) appComponents.stream()
        .filter(obj -> componentClass.isAssignableFrom(obj.getClass()))
        .findAny()
        .orElseThrow(() -> new NoSuchElementException(String.format("There is no app component by this class: %s", componentClass.getName())));
  }

  @Override
  public <C> C getAppComponent(String componentName) {
    return (C) appComponentsByName.keySet().stream()
        .filter(name -> name.equals(componentName))
        .findAny()
        .orElseThrow(() -> new NoSuchElementException(String.format("There is no app component by this name: %s", componentName)));
  }
}
