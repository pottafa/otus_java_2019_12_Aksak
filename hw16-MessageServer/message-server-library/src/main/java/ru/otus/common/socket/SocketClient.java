package ru.otus.common.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.common.ms.Message;
import ru.otus.common.ms.MsClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketClient {
    private static Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private final int PORT;
    private final String HOST;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final MsClient msClient;

    public SocketClient(int port, String host, MsClient msClient) {
        this.PORT = port;
        this.HOST = host;
        this.msClient = msClient;
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

    public void go() throws IOException {

        try (Socket clientSocket = new Socket(HOST, PORT)) {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());

            out.writeObject(msClient.getName());

            readMessages();
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private void readMessages() throws IOException, ClassNotFoundException {
        while (true) {
            Message msg = (Message) in.readObject();
            msgHandler.submit(() -> msClient.handle(msg));
        }
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (Exception ex) {
            logger.error("Message was not sent to: {}", message.getTo(), ex);
        }
    }

}
