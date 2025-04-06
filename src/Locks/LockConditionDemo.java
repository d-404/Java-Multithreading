package Locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionDemo {
    private static final Integer MAX_SIZE = 5;
    private final Lock lock = new ReentrantLock();
    private final Queue<Integer> buffer = new LinkedList<>();
    private final Condition bufferNotFull = lock.newCondition();
    private final Condition bufferNotEmpty = lock.newCondition();

    public static void main(String[] args) {
        LockConditionDemo lockDemo = new LockConditionDemo();
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++)
                    lockDemo.produce(i);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++)
                    lockDemo.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        });
        producerThread.start();
        consumerThread.start();
    }

    private void produce(int item) throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == MAX_SIZE)
                bufferNotFull.await();
            buffer.offer(item);
            System.out.println("Produced >> " + item);
            bufferNotEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    private void consume() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.isEmpty())
                bufferNotEmpty.await();
            System.out.println("Consumed << " + buffer.poll());
            bufferNotFull.signal();
        } finally {
            lock.unlock();
        }
    }
}
