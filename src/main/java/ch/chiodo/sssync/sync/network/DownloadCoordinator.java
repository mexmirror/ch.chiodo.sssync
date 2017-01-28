package ch.chiodo.sssync.sync.network;

import java.util.*;
import java.util.concurrent.*;

public class DownloadCoordinator extends Observable implements Observer {
    private ForkJoinPool threadPool = ForkJoinPool.commonPool();
    private Queue<DownloadTask> queue;

    public DownloadCoordinator(Queue<DownloadTask> queue) {
        this.queue = queue;
    }

    @Override
    public void update(Observable o, Object arg) {
        DownloadTask task = queue.poll();
        if(task == null) return;
        threadPool.submit(task);
        setChanged();
        notifyObservers(task);
    }
}
