package ru.otus.homework;

public class SynchronizedSequence {
  private final Object lock = new Object();

  public static void main(String[] args) {
    SynchronizedSequence sequence = new SynchronizedSequence();
    sequence.start();
  }

  public void sequence() {
    for (int i = 1; i <= 10; i++) syncCount(i);
    for (int i = 9; i > 0; i--) syncCount(i);
    synchronized (lock) {
      lock.notifyAll();
    }
  }


  private void syncCount(int count) {
    try {
        synchronized (lock) {
          System.out.println(Thread.currentThread().getName() + " " + count);
          lock.notify();
          lock.wait();
          Thread.sleep(500);
        }
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }


  public void start() {
    Thread thread1 = new Thread(this::sequence);
    Thread thread2 = new Thread(this::sequence);

    thread1.start();
    thread2.start();

  }
}
