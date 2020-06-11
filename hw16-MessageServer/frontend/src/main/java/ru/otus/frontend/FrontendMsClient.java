package ru.otus.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.otus.common.ms.MessageType;
import ru.otus.common.ms.MsClientImpl;
import ru.otus.common.socket.SocketClient;
import ru.otus.frontend.front.FrontendService;
import ru.otus.frontend.front.handlers.GetAllUsersDataResponseHandler;
import ru.otus.frontend.front.handlers.SaveUserDataResponseHandler;

@Component
public class FrontendMsClient extends MsClientImpl {
    private FrontendService frontendService;

    public FrontendMsClient(@Value("${frontend_service_client_name}") String name, FrontendService frontendService, @Lazy SocketClient socketClient) {
        super(name, socketClient);
        this.frontendService = frontendService;
        addHandler(MessageType.ALL_USERS_DATA, new GetAllUsersDataResponseHandler(frontendService));
        addHandler(MessageType.USER_DATA, new SaveUserDataResponseHandler(frontendService));
    }
}
