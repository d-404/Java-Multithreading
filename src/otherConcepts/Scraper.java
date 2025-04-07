package otherConcepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Scraper {
    public static void main(String[] args) {
        try (ExecutorService service = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 15; i++) {
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ScrapeService.INSTANCE.scrape();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }
    }
}

enum ScrapeService {
    INSTANCE;
    private final Semaphore semaphore = new Semaphore(3);

    public void scrape() throws InterruptedException {
        try {
            semaphore.acquire();
            invokeScrapeBot();
        } finally {
            semaphore.release();
        }
    }

    private void invokeScrapeBot() throws InterruptedException {
        System.out.println("Scraping Data....");
    }
}
