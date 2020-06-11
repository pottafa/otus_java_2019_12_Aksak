package ru.otus.dbServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.otus.common.ms.MessageType;
import ru.otus.common.ms.MsClientImpl;
import ru.otus.common.socket.SocketClient;
import ru.otus.dbServer.core.service.DBServiceUser;
import ru.otus.dbServer.handlers.GetAllUsersData;
import ru.otus.dbServer.handlers.SaveUserHandler;

@Component
public class DbMsClient extends MsClientImpl {
    private DBServiceUser dbServiceUser;

    public DbMsClient(@Value("${database_service_client_name}") String name, DBServiceUser frontendService, @Lazy SocketClient socketClient) {
        super(name, socketClient);
        this.dbServiceUser = frontendService;
        addHandler(MessageType.ALL_USERS_DATA, new GetAllUsersData(frontendService));
        addHandler(MessageType.USER_DATA, new SaveUserHandler(frontendService));
    }
}
