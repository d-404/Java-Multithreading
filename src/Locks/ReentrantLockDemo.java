package Locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int sharedData = 0;

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        for (int i = 0; i < 10; i++)
            new Thread(demo::methodA).start();
    }

    public void methodA() {
        lock.lock();
        try {
            sharedData++;
            System.out.println("MethodA -> sharedData : " + sharedData);
            methodB();
        } finally {
            lock.unlock();
        }
    }

    public void methodB() {
        lock.lock();
        try {
            sharedData++;
            System.out.println("MethodB -> sharedData : " + sharedData);
        } finally {
            lock.unlock();
        }
    }
}
