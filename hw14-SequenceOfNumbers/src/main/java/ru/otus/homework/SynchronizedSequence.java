package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedSequence {
  private static Logger logger = LoggerFactory.getLogger(SynchronizedSequence.class);
  private volatile Thread currentThread;

  public static void main(String[] args) {
    SynchronizedSequence sequence = new SynchronizedSequence();
    sequence.start();
  }

  public void sequence() {
    for (int i = 1; i <= 10; i++) {
      syncCount(i);
    }
    for (int i = 9; i > 0; i--) {
      syncCount(i);
    }
    currentThread.interrupt();
  }

  private synchronized void syncCount(int count) {
    try {
      currentThread = Thread.currentThread();
      notify();
      logger.info(String.valueOf(count));
      while (currentThread.equals(Thread.currentThread())) {
        wait();
      }
      Thread.sleep(100);
    } catch (InterruptedException ex) {
      logger.info(currentThread.getName() + " has been interrupted");
      currentThread.interrupt();
    }
  }


  public void start() {
    Thread thread1 = new Thread(this::sequence);
    Thread thread2 = new Thread(this::sequence);

    thread1.start();
    thread2.start();

  }
}
