package ru.otus.dbServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import ru.otus.common.ms.MessageType;
import ru.otus.common.ms.MsClient;
import ru.otus.common.ms.MsClientImpl;
import ru.otus.common.socket.SocketClient;
import ru.otus.dbServer.core.service.DBServiceUser;
import ru.otus.dbServer.handlers.GetAllUsersData;
import ru.otus.dbServer.handlers.SaveUserHandler;

@Configuration
public class DbServerConfig {

    @Value("${database_service_client_name}")
    private String dbClientName;
    @Value("${port}")
    private int port;
    @Value("${host}")
    private String host;

    @Bean
    public MsClient dbMsClient(DBServiceUser service, @Lazy SocketClient socketClient) {
        MsClient msDbClient = new MsClientImpl(dbClientName, socketClient);
        msDbClient.addHandler(MessageType.ALL_USERS_DATA, new GetAllUsersData(service));
        msDbClient.addHandler(MessageType.USER_DATA, new SaveUserHandler(service));
        return msDbClient;
    }

    @Bean
    public SocketClient dbSocketClient(MsClient dbMsClient) {
        return new SocketClient(port, host, dbMsClient);
    }

}
