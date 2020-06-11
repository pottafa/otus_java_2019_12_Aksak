package ru.otus.ms.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ProcessRunnerImpl implements ProcessRunner {
    private static Logger logger = LoggerFactory.getLogger(ProcessRunnerImpl.class);
    private static ExecutorService service = Executors.newFixedThreadPool(4);
    private Process process;


    public void run(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            service.submit(new ProcessListener(process.getInputStream()));
        } catch (Exception e) {
            logger.error("Process failed ", e);
        }
    }

    private class ProcessListener implements Runnable {
        private final InputStream inputStream;

        private ProcessListener(InputStream is) {
            this.inputStream = is;
        }

        @Override
        public void run() {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                logger.info("Process started: {}", process.isAlive());
                while ((line = bufferedReader.readLine()) != null) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
