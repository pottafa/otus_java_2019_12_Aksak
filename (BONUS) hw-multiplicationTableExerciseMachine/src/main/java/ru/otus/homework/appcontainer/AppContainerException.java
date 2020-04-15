package ru.otus.homework.appcontainer;

public class AppContainerException extends Exception {
    AppContainerException(Throwable cause) {
        super(cause);
    }
    AppContainerException(String msg) {
        super(msg);
    }
}
