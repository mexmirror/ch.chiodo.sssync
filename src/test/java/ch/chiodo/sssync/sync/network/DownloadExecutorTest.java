package ch.chiodo.sssync.sync.network;

import org.joda.time.Duration;
import org.junit.Test;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import static org.mockito.Mockito.*;


public class DownloadExecutorTest {
    @Test
    public void executorDequeueCall() throws Exception {
        DownloadTask t = mock(DownloadTask.class);
        Deque<DownloadTask> q = new ConcurrentLinkedDeque<DownloadTask>(){{
            addLast(t);
            addLast(t);
        }};
        DownloadExecutor e = new DownloadExecutor(q);
        e.start();
        Thread.sleep(Duration.standardSeconds(2).getMillis());
        verify(t, times(2)).run();
        e.stop();
    }

    private class FakeDownloadTask extends DownloadTask {
        @Override
        public void run() {

        }
    }
}