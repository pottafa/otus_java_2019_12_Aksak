package ru.otus.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import ru.otus.common.ms.MessageType;
import ru.otus.common.ms.MsClient;
import ru.otus.common.ms.MsClientImpl;
import ru.otus.common.socket.SocketClient;
import ru.otus.frontend.front.FrontendService;
import ru.otus.frontend.front.handlers.GetAllUsersDataResponseHandler;
import ru.otus.frontend.front.handlers.SaveUserDataResponseHandler;

@Configuration
public class FrontendServerConfig {

    @Value("${frontend_service_client_name}")
    private String dbClientName;
    @Value("${port}")
    private int port;
    @Value("${host}")
    private String host;

    @Bean
    public MsClient frontendMsClient(FrontendService service, @Lazy SocketClient socketClient) {
        MsClient msFrontendClient = new MsClientImpl(dbClientName, socketClient);
        msFrontendClient.addHandler(MessageType.ALL_USERS_DATA, new GetAllUsersDataResponseHandler(service));
        msFrontendClient.addHandler(MessageType.USER_DATA, new SaveUserDataResponseHandler(service));
        return msFrontendClient;
    }

    @Bean
    public SocketClient frontendSocketClient(MsClient frontendMsClient) {
        return new SocketClient(port, host, frontendMsClient);
    }

}
