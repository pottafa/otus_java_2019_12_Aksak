package ru.otus.homework.gcLoad;

import java.util.*;

public class Loading {
    private int loopNumber;
    public static long majorIterationsCount = 0;

    public Loading(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    private List<Object> memoryDestroyer = new ArrayList<>();
    private int size = 700000;

    public void run() {
        for (int i = 0; i < loopNumber; i++) {
            Object[] forGarbage = new Object[size];
            majorIterationsCount++;
            for (int x = 0; x < size; x++) {
                forGarbage[x] = x;
            }
            memoryDestroyer.add(new int[10000]);
        }
    }
}
