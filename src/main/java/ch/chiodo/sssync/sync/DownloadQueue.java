package ch.chiodo.sssync.sync;

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
        return queue.removeLast();
    }

    public boolean hasElementEnqueued() {
        return queue.peekLast() != null;
    }

    public int size() {
        return queue.size();
    }
}