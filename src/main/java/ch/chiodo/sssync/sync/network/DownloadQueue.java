package ch.chiodo.sssync.sync.network;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DownloadQueue {
    private Deque<DownloadTask> queue;

    public DownloadQueue() {
        queue = new ConcurrentLinkedDeque<>();
    }

    public void enqueue(DownloadTask download) {
        queue.addFirst(download);
    }

    public DownloadTask dequeue() {
        return queue.pollLast();
    }

    public boolean isEmtpy() {
        return queue.peekLast() == null;
    }

    public void clear() {
        queue.clear();
    }

    boolean contains(DownloadTask task) {
        return queue.contains(task);
    }
}