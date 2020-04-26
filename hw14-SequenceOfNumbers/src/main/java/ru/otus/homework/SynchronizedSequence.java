package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedSequence {
  private static final int SEQUENCE_NUMBER = 10;
  private static Logger logger = LoggerFactory.getLogger(SynchronizedSequence.class);
  private Thread lastThread;

  public static void main(String[] args) {
    SynchronizedSequence sequence = new SynchronizedSequence();
    sequence.start();
  }

  public void sequence() {
    for (int i = 1; i <= SEQUENCE_NUMBER; i++) {
      syncCount(i);
    }
    for (int i = SEQUENCE_NUMBER - 1; i > 0; i--) {
      syncCount(i);
    }
  }

  private synchronized void syncCount(int count) {
    try {
      while (lastThread.equals(Thread.currentThread())) {
        wait();
      }
      logger.info(String.valueOf(count));
      lastThread = Thread.currentThread();
      Thread.sleep(100);
      notifyAll();
    } catch (InterruptedException ex) {
      logger.info(lastThread.getName() + " has been interrupted");
      lastThread.interrupt();
    }
  }


  public void start() {
    Thread thread1 = new Thread(this::sequence);
    lastThread = thread1;
    Thread thread2 = new Thread(this::sequence);

    thread1.start();
    thread2.start();

  }
}
