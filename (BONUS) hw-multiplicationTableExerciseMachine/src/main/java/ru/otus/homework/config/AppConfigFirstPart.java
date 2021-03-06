package ru.otus.homework.config;

import ru.otus.homework.appcontainer.api.AppComponent;
import ru.otus.homework.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.homework.services.*;

import java.util.Scanner;

@AppComponentsContainerConfig(order = 1)
public class AppConfigFirstPart {

    @AppComponent(order = 0, name = "equationPreparer")
    public EquationPreparer equationPreparer(){
        return new EquationPreparerImpl();
    }

    @AppComponent(order = 2, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }

  @AppComponent(order = 1, name = "ioService")
  public IOService ioService() {
    return new IOServiceConsole(System.out, new Scanner(System.in));
  }

}
