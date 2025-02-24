package executorService;

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            Future<Integer> result = executorService.submit(new ReturnValueTask());
            result.cancel(true);
            result.isCancelled();
            result.isDone();
            System.out.println("Result : " + result.get(6, TimeUnit.SECONDS));
            System.out.println("main thread execution completed");
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}

class ReturnValueTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(5000);
        return 12;
    }
}
