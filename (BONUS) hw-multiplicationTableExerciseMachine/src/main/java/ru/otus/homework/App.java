package ru.otus.homework;

import ru.otus.homework.appcontainer.AppComponentsContainerImpl;
import ru.otus.homework.appcontainer.AppContainerException;
import ru.otus.homework.appcontainer.api.AppComponentsContainer;
import ru.otus.homework.config.AppConfigFirstPart;
import ru.otus.homework.config.AppConfigSecondPart;
import ru.otus.homework.services.LoggablePlayerService;

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

        //    With one config class
        //    AppComponentsContainer container = new AppComponentsContainerImpl(AppConfigFull.class);
        //    GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        //    gameProcessor.startGame();

        //    Getting proxy app component that stands before the original class
        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfigFirstPart.class, AppConfigSecondPart.class);
        LoggablePlayerService loggablePlayerService = container.getAppComponent(LoggablePlayerService.class);
        loggablePlayerService.getPlayer();
    }
}
