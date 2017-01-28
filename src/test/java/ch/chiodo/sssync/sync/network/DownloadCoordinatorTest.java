package ch.chiodo.sssync.sync.network;

import org.joda.time.Duration;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import static org.mockito.Mockito.*;


public class DownloadCoordinatorTest {
    @Test
    public void executorDequeueCall() throws Exception {
        DownloadTask t = mock(DownloadTask.class);
        Queue<DownloadTask> queue = new ArrayDeque<DownloadTask>(){{
            add(t);
        }};
        DownloadCoordinator dc = new DownloadCoordinator(new FakeThreadPool(), queue);
        dc.update(null, null);
        verify(t, times(1)).run();
    }

    private class FakeThreadPool extends ForkJoinPool {
        @Override
        public ForkJoinTask<?> submit(Runnable task) {
            task.run();
            //noinspection ConstantConditions
            return null;
        }
    }
}