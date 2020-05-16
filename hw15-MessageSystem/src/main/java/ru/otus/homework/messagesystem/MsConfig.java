package ru.otus.homework.messagesystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.dataBase.core.service.DBServiceUser;
import ru.otus.homework.dataBase.handlers.GetAllUsersData;
import ru.otus.homework.dataBase.handlers.SaveUserHandler;
import ru.otus.homework.front.FrontendService;
import ru.otus.homework.front.handlers.GetAllUsersDataResponseHandler;
import ru.otus.homework.front.handlers.SaveUserDataResponseHandler;
import ru.otus.homework.messagesystem.*;

@Configuration
public class MsConfig {

    @Value("${frontend_service_client_name}")
    private String frontendServiceClientName;
    @Value("${database_service_client_name}")
    private String databaseServiceClientName;

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public MsClient frontendMsClient(MessageSystem messageSystem, FrontendService frontendService) {
        MsClient frontendClient = new MsClientImpl(frontendServiceClientName, messageSystem);
        frontendClient.addHandler(MessageType.ALL_USERS_DATA, new GetAllUsersDataResponseHandler(frontendService));
        frontendClient.addHandler(MessageType.USER_DATA, new SaveUserDataResponseHandler(frontendService));
        messageSystem.addClient(frontendClient);
        return frontendClient;
    }

    @Bean
    public MsClient dbClient(MessageSystem messageSystem, DBServiceUser dbServiceUser) {
        MsClient databaseMsClient = new MsClientImpl(databaseServiceClientName, messageSystem);
        databaseMsClient.addHandler(MessageType.USER_DATA, new SaveUserHandler(dbServiceUser));
        databaseMsClient.addHandler(MessageType.ALL_USERS_DATA, new GetAllUsersData(dbServiceUser));
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }



}
