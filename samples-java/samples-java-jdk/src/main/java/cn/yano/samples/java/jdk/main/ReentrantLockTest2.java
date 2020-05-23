package cn.yano.samples.java.jdk.main;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest2 {

    private static int sum = 0;
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static Lock fairLock = new ReentrantLock(true);

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(new Task1(0, 1)).start();
            new Thread(new Task1(2, 3)).start();
            new Thread(new Task1(4, 5)).start();
            new Thread(new Task1(6, 7)).start();
            new Thread(new Task1(8, 9)).start();
        }

    }

    static class Task1 implements Runnable {

        private int start = 0;
        private int end = 0;

        public Task1(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            System.out.println("Task" + start + "_" + end + " init");
            lock.lock();
            while (sum % 10 < start || sum % 10 > end) {
                try {
                    System.out.println("Task" + start + "_" + end + " wait result is " + sum);
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            condition.signalAll();
            System.out.println("Task" + start + "_" + end + " start");
            sum = sum + 1;
            System.out.println("Task" + start + "_" + end + " end result is " + sum);
            lock.unlock();
        }
    }
}
