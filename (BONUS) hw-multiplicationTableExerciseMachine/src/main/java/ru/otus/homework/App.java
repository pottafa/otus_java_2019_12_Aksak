package ru.otus.homework;

import ru.otus.homework.appcontainer.AppComponentsContainerImpl;
import ru.otus.homework.appcontainer.AppContainerException;
import ru.otus.homework.appcontainer.api.AppComponentsContainer;
import ru.otus.homework.services.GameProcessor;

/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.

PS Приложение представляет из себя тренажер таблицы умножения)
*/

public class App {

  public static void main(String[] args) throws AppContainerException {
    //    With several config classes
    //    AppComponentsContainer container = new AppComponentsContainerImpl(AppConfigFirstPart.class, AppConfigSecondPart.class);
    //    GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
    //    gameProcessor.startGame();

    // Package as parameter
    AppComponentsContainer container = new AppComponentsContainerImpl("ru.otus.homework.config");
    GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
    gameProcessor.startGame();
  }
}
