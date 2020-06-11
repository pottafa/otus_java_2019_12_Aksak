package ru.otus.ms.process;

import java.io.IOException;

public interface ProcessRunner {
    void run(String command) throws IOException;
}
