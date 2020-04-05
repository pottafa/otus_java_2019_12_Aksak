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
  private final Map<Method, Object> annotatedMethods = new HashMap<>();

  public AppComponentsContainerImpl(Class<?> initialConfigClass) {
    try {
      processConfig(initialConfigClass);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
    try {
      processConfig(initialConfigClasses);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void processConfig(Class<?>... configClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    for (Class<?> aClass : configClass) {
      checkConfigClass(aClass);
      parseMethods(aClass);
    }
    var listIterator = sortMethods().iterator();
    while (listIterator.hasNext()) {
      var method = listIterator.next();
      var annotationNameValue = method.getAnnotation(AppComponent.class).name();
      Object methodResult;
      if (method.getParameterCount() == 0) {
        methodResult = method.invoke(annotatedMethods.get(method));
      } else {
        List<Object> params = getMethodParams(method);
        if (params == null) continue;
        methodResult = method.invoke(annotatedMethods.get(method), params.toArray());
      }
      appComponents.add(methodResult);
      appComponentsByName.put(annotationNameValue, methodResult);
      listIterator.remove();
    }
  }

  private List<Method> sortMethods() {
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
    if (params.size() == method.getParameterCount()) return params;
    return null;
  }

  private void parseMethods(Class<?> configClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
    return null;
  }

  @Override
  public <C> C getAppComponent(String componentName) {
    for (String name : appComponentsByName.keySet()) {
      if (name.equals(componentName)) return (C) appComponentsByName.get(name);
    }
    return null;
  }
}
