package ch.chiodo.sssync.sync.network;

import org.joda.time.Duration;
import org.junit.Test;

import static org.mockito.Mockito.*;


public class DownloadExecutorTest {
    @Test
    public void executorDequeueCall() throws Exception{
        DownloadQueue q = mock(DownloadQueue.class);
        when(q.isEmpty()).thenReturn(false).thenReturn(false).thenReturn(true);
        when(q.dequeue()).thenReturn(new FakeDownloadTask());
        DownloadExecutor e = new DownloadExecutor(q);
        e.start();
        Thread.sleep(Duration.standardSeconds(2).getMillis());
        verify(q, times(2)).dequeue();
        e.stop();
    }

    private class FakeDownloadTask extends DownloadTask {
        @Override
        public void run() {

        }
    }
}