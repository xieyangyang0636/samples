package cn.yano.samples.java.jdk.main;

import java.io.IOException;

public class SynchronizedObjectTest {

    private static transient int sum = 0;
    private final static Object flag = new Object();

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10; i++) {
            new Thread(new Task1(0,1)).start();
            new Thread(new Task1(2,3)).start();
            new Thread(new Task1(4,5)).start();
            new Thread(new Task1(6,7)).start();
            new Thread(new Task1(8,9)).start();
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
            synchronized (flag) {
                System.out.println("Task" + start + "_" + end + " init");
                while (sum % 10 < start || sum % 10 > end) {
                    try {
                        System.out.println("Task" + start + "_" + end + " wait result is " + sum);
                        flag.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Task" + start + "_" + end + " start");
                sum = sum + 1;
                System.out.println("Task" + start + "_" + end + " end result is " + sum);
                flag.notifyAll();
            }
        }
    }


}
