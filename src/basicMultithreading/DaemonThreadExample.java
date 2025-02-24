package basicMultithreading;

public class DaemonThreadExample {
    public static void main(String[] args) {
        Thread bgThread = new Thread(new DaemonThreadHelper());
        Thread userThread = new Thread(new UserThreadHelper());
        bgThread.setDaemon(true);
        bgThread.start();
        userThread.start();
    }
}

class DaemonThreadHelper implements Runnable {
    @Override
    public void run() {
        int count = 0;
        while (count < 500) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
            System.out.println("Daemon helper running..");
        }
    }
}

class UserThreadHelper implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User Thread done with execution...");
    }
}
