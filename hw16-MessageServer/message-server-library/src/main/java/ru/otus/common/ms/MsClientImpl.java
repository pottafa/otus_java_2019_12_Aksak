package ru.otus.common.ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.common.ms.common.Serializers;
import ru.otus.common.socket.SocketClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MsClientImpl implements MsClient {
    private final Map<String, RequestHandler> handlers = new ConcurrentHashMap<>();
    private static Logger logger = LoggerFactory.getLogger(MsClientImpl.class);
    private String name;
    private SocketClient socketClient;

    public MsClientImpl(String name, SocketClient socketClient) {
        this.name = name;
        this.socketClient = socketClient;
    }

    private final ExecutorService msgHandler = Executors.newFixedThreadPool(2,
            new ThreadFactory() {
                private final AtomicInteger threadNameSeq = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable runnable) {
                    Thread thread = new Thread(runnable);
                    thread.setName("msg-handler-thread-" + threadNameSeq.incrementAndGet());
                    return thread;
                }
            });

    @Override
    public void sendMessage(Message message) {
        socketClient.sendMessage(message);
    }

    @Override
    public void addHandler(MessageType type, RequestHandler requestHandler) {
        this.handlers.put(type.getValue(), requestHandler);
    }

    @Override
    public void handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            RequestHandler requestHandler = handlers.get(msg.getType());
            if (requestHandler != null) {
                requestHandler.handle(msg).ifPresent(this::sendMessage);
            } else {
                logger.error("handler not found for the message type:{}", msg.getType());
            }
        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
    }

    @Override
    public <T> Message produceMessage(String to, T data, MessageType msgType) {
        return new Message(name, to, null, msgType.getValue(), Serializers.serialize(data));
    }

    @Override
    public String getName() {
        return name;
    }
}
