package ru.otus.homework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.dataBase.core.service.DBServiceUser;
import ru.otus.homework.dataBase.handlers.GetAllUsersData;
import ru.otus.homework.dataBase.handlers.SaveUserHandler;
import ru.otus.homework.front.FrontendService;
import ru.otus.homework.front.handlers.GetAllUsersDataResponseHandler;
import ru.otus.homework.messagesystem.MessageSystem;
import ru.otus.homework.messagesystem.MessageType;
import ru.otus.homework.messagesystem.MsClient;
import ru.otus.homework.messagesystem.MsClientImpl;

@Configuration
@ComponentScan
public class MsConfig {

  private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
  private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

  @Bean
  public MsClient frontendMsClient(MessageSystem messageSystem, FrontendService frontendService) {
    MsClient frontendClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
    frontendClient.addHandler(MessageType.ALL_USERS_DATA, new GetAllUsersDataResponseHandler(frontendService));
    messageSystem.addClient(frontendClient);
    return frontendClient;
  }

  @Bean
  public MsClient dbClient(MessageSystem messageSystem, DBServiceUser dbServiceUser) {
    MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
    databaseMsClient.addHandler(MessageType.USER_DATA, new SaveUserHandler(dbServiceUser));
    databaseMsClient.addHandler(MessageType.ALL_USERS_DATA, new GetAllUsersData(dbServiceUser));
    messageSystem.addClient(databaseMsClient);
    return databaseMsClient;
  }

  @Bean(name = "dbClientName")
  public String getDatabaseServiceClientName() {
    return DATABASE_SERVICE_CLIENT_NAME;
  }


}
