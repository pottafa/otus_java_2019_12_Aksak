# Автоматическое логирование

# Задание

Разработайте такой функционал:
метод класса можно пометить самодельной аннотацией @Log, например, так:

```java
class TestLogging {
@Log
public void calculation(int param) {};
}
```

При вызове этого метода "автомагически" в консоль должны логироваться значения параметров.
Например так.
```java
class Demo {
public void action() {
new TestLogging().calculation(6);
}
}
```

В консоле дожно быть:
```bash
executed method: calculation, param: 6
```
Обратите внимание: явного вызова логирования быть не должно.