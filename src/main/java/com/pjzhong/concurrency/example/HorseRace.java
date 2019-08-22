package com.pjzhong.concurrency.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * A simple horse race (using CyclicBarrier) example come from Thinking in JAVA four edition
 */
class Horse implements Runnable {

  private static int counter = 0;
  private static Random ran = new Random(47);

  private int id = counter++;
  private int strides = 0;
  private CyclicBarrier barrier;

  public Horse(CyclicBarrier barrier) {
    this.barrier = barrier;
  }

  @Override
  public void run() {
    try {
      while (!Thread.interrupted()) {
        synchronized (this) {
          strides += ran.nextInt(3);
        }
        barrier.await();
      }
    } catch (InterruptedException e) {

    } catch (BrokenBarrierException e) {
      throw new RuntimeException(e);
    }
  }

  public synchronized int getStrides() {
    return strides;
  }

  public String tracks() {
    StringBuilder s = new StringBuilder();
    for (int i = 0, size = getStrides(); i < size; i++) {
      s.append("*");
    }
    s.append(" ").append(id);
    return s.toString();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Horse{");
    sb.append("id=").append(id);
    sb.append('}');
    return sb.toString();
  }
}

public class HorseRace {

  private static final int FINISH_LINE = 150;

  /**
   * The threads in this threadpool must greater or equal to the horses.size(), otherwise this
   * program may block forever
   */
  private ExecutorService exec = Executors.newCachedThreadPool();

  private List<Horse> horses = new ArrayList<>();
  private CyclicBarrier barrier;
  private String line;
  private int pause;

  public HorseRace(int nHouse, int pause) {
    this.pause = pause;
    StringBuilder LineBuilder = new StringBuilder();
    for (int i = 0; i < FINISH_LINE; i++) {
      LineBuilder.append('=');
    }
    line = LineBuilder.toString();
    barrier = new CyclicBarrier(nHouse, this::showHorseRace);

    for (int i = 0; i < nHouse; i++) {
      Horse horse = new Horse(barrier);
      horses.add(horse);
    }
  }

  private void showHorseRace() {
    System.out.println(line);
    horses.forEach((h) -> System.out.println(h.tracks()));

    for (Horse horse : horses) {
      if (FINISH_LINE <= horse.getStrides()) {
        System.out.println(horse + "WON!");
        exec.shutdownNow();
        return;
      }
    }
    try {
      TimeUnit.MILLISECONDS.sleep(pause);
    } catch (InterruptedException e) {
      System.out.println("barrier-action sleep interrupted");
    }
  }

  public void start() {
    horses.forEach(exec::execute);
  }

  public static void main(String[] args) {
    int nHorses = 7, pause = 200;
    HorseRace race = new HorseRace(nHorses, pause);
    race.start();
  }
}
