package ru.otus.homework.config;

import ru.otus.homework.appcontainer.api.AppComponent;
import ru.otus.homework.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.homework.services.*;

import java.util.Scanner;

@AppComponentsContainerConfig(order = 2)
public class AppConfigSecondPart {


  @AppComponent(order = 1, name = "playerServiceLoggable")
  public LoggablePlayerService aaaaaaaaa(PlayerService playerService) {
    return new LoggablePlayerService(playerService);
  }

    @AppComponent(order = 2, name = "gameProcessor")
    public GameProcessor gameProcessor(IOService ioService,
                                       PlayerService playerService,
                                       EquationPreparer equationPreparer) {
        return new GameProcessorImpl(ioService, equationPreparer, playerService);
    }

    @AppComponent(order = 0, name = "ioService")
    public IOService ioService() {
        return new IOServiceConsole(System.out, new Scanner(System.in));
    }

}
