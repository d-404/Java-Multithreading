package otherConcepts;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariableDemo {
    private static int count = 0;
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread one = new Thread(() ->
        {
            for (int i = 0; i < 10000; i++)
//                count++;
                counter.incrementAndGet();
        });

        Thread two = new Thread(() ->
        {
            for (int i = 0; i < 10000; i++)
//                count++;
                counter.incrementAndGet();
        });

        one.start();
        two.start();

        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        System.out.println("Count value : " + counter);
    }
}
