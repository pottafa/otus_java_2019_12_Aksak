package ru.otus.dbServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.common.socket.SocketClient;

@Component
public class DbSocketClient extends SocketClient {

    public DbSocketClient(@Value("${port}") int port, @Value("${host}") String host, DbMsClient dbMsClient) {
        super(port, host, dbMsClient);
    }
}
