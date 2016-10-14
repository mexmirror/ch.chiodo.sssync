package ch.chiodo.sssync.sync;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DownloadQueue {
    private Deque<Runnable> queue;

    public DownloadQueue() {
        queue = new ConcurrentLinkedDeque<>();
    }

    public void enqueue(Runnable download) {
        queue.addFirst(download);
    }

    public Runnable dequeue() {
        return queue.removeLast();
    }

    public boolean hasElementEnqueued() {
        return queue.peekLast() != null;
    }

    public int size() {
        return queue.size();
    }

    public void clear() {
        queue.clear();
    }
}