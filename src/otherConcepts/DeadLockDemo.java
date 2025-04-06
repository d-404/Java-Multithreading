package otherConcepts;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    private final Lock lockA = new ReentrantLock();
    private final Lock lockB = new ReentrantLock();

    public void workerOne() {
        lockA.lock();
        System.out.println("Worker One acquired lockA");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        lockB.lock();
        System.out.println("Worker One acquired lockB");
        lockA.unlock();
        lockB.unlock();
    }

    public void workerTwo() {
        lockB.lock();
        System.out.println("Worker Two acquired lockB");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        lockA.lock();
        System.out.println("Worker Two acquired lockA");
        lockA.unlock();
        lockB.unlock();
    }

    public static void main(String[] args) {
        DeadLockDemo deadLockDemo = new DeadLockDemo();
        new Thread(deadLockDemo::workerOne, "Worker One").start();
        new Thread(deadLockDemo::workerTwo, "Worker Two").start();

        new Thread(() ->
        {
            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            while (true) {
                long[] threadIds = bean.findDeadlockedThreads();
                if (threadIds != null) {
                    ThreadInfo[] threadIInfo = bean.getThreadInfo(threadIds);
                    System.out.println("Dead Lock detected");
                    for (long threadId : threadIds) {
                        System.out.println("Thread with id : " + threadId + " is in deadlock.");
                    }
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }
        }).start();
    }
}
