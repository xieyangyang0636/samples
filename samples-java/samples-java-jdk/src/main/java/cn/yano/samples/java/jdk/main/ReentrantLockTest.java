package cn.yano.samples.java.jdk.main;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    private static int num = 0;
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static Lock fairLock = new ReentrantLock(true);

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(new Task(i)).start();
        }

        // 等待所有线程挂起
        Thread.sleep(2000);

        lock.lock();
        System.out.println("Main Thread start");
        condition.signalAll();
        lock.unlock();
        System.out.println("Main Thread end");
    }

    static class Task implements Runnable {
        private int name;

        public Task(int name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Thread" + name + " init");
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread" + name + " start");
            num = num + 1;
            System.out.println("Thread" + name + " end, result : " + num);
            lock.unlock();
        }
    }
}
