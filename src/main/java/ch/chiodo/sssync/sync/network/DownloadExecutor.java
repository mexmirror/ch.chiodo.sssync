package ch.chiodo.sssync.sync.network;

import java.util.concurrent.*;

public class DownloadExecutor {
    private ForkJoinPool threadPool = ForkJoinPool.commonPool();
    private final DownloadQueue queue;
    private ScheduledFuture<?> handle;

    public DownloadExecutor(DownloadQueue queue) {
        this.queue = queue;
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable r = () -> {
            while(!queue.isEmpty()) {
                threadPool.submit(queue.dequeue());
            }
        };
        handle = scheduler.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        handle.cancel(false);
        queue.clear();
    }
}
