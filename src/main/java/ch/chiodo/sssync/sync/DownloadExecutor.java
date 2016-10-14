package ch.chiodo.sssync.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ForkJoinPool;

public class DownloadExecutor {
    private ForkJoinPool threadPool = ForkJoinPool.commonPool();
    private final DownloadQueue queue;
    private Thread dispatcher;
    private Logger logger = LoggerFactory.getLogger(DownloadExecutor.class);

    public DownloadExecutor(DownloadQueue queue) {
        this.queue = queue;
    }

    public void start() {
        dispatcher = new Thread(() -> {
            //noinspection InfiniteLoopStatement
            while(true) {
                while (queue.hasElementEnqueued()) {
                    threadPool.submit(queue.dequeue());
                }
                try {
                    Thread.sleep(Duration.ofSeconds(1).toMillis());
                } catch (InterruptedException ex) {
                    logger.info(String.format("DownloadExecutor was interrupted. Cause: %s", ex.getMessage()));
                }
            }
        });
    }

    public void stop() {
        dispatcher.interrupt();
        queue.clear();
    }
}
