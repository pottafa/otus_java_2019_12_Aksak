package ru.otus.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.common.socket.SocketClient;

@Component
public class FrontendSocketClient extends SocketClient {

    public FrontendSocketClient(@Value("${port}") int port, @Value("${host}") String host, FrontendMsClient frontendMsClient) {
        super(port, host, frontendMsClient);
    }

}
