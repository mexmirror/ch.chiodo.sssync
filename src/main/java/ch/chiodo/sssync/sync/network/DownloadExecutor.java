package ch.chiodo.sssync.sync.network;

import java.time.Duration;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class DownloadExecutor {
    private ForkJoinPool threadPool = ForkJoinPool.commonPool();
    private Deque<DownloadTask> queue;
    private Timer timer;


    public DownloadExecutor(Deque<DownloadTask> queue) {
        this.queue = queue;
    }

    public void start() {
        timer = new Timer();
        timer.schedule(new QueueTimerTask(), Duration.ofSeconds(1).toMillis());
    }

    public void stop() {
        timer.cancel();
        queue.clear();
    }

    private class QueueTimerTask extends TimerTask {
        @Override
        public void run() {
            //TODO: This is not very clever
            while(!queue.isEmpty()){
                threadPool.submit(queue.poll());
            }
        }
    }
}
