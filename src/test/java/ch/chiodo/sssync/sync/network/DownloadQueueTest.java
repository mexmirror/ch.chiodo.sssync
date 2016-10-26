package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.sync.network.DownloadQueue;
import ch.chiodo.sssync.sync.network.DownloadTask;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class DownloadQueueTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void enqueue() throws Exception {
        DownloadQueue q = new DownloadQueue();
        DownloadTask a = mock(DownloadTask.class);
        q.enqueue(a);
        assertThat(q.contains(a), is(true));
    }

    @Test
    public void dequeue() throws Exception {
        DownloadQueue q = new DownloadQueue();
        DownloadTask a = mock(DownloadTask.class);
        q.enqueue(a);
        DownloadTask result = q.dequeue();
        assertThat(result, is(a));
        assertThat(q.isEmtpy(), is(true));
    }

    @Test
    public void queueOrder() throws Exception {
        DownloadQueue q = new DownloadQueue();
        DownloadTask d1 = mock(DownloadTask.class);
        q.enqueue(d1);
        DownloadTask d2 = mock(DownloadTask.class);
        q.enqueue(d2);
        DownloadTask d3 = mock(DownloadTask.class);
        q.enqueue(d3);
        assertThat(q.isEmtpy(), is(false));
        List<DownloadTask> list = new ArrayList<>();
        while(!q.isEmtpy()) {
            list.add(q.dequeue());
        }
        assertThat(list, containsInOrder(d1, d2, d3));
    }

    @Test
    public void clear() throws Exception {
        DownloadQueue q = new DownloadQueue();
        DownloadTask d = mock(DownloadTask.class);
        q.enqueue(d);
        q.clear();
        assertThat(q.isEmtpy(), is(true));
    }

    private Matcher<List<DownloadTask>> containsInOrder (DownloadTask... expected) {
        return new BaseMatcher<List<DownloadTask>>() {
            @Override
            public boolean matches(Object result) {
                if(!(result instanceof List)) {
                    return false;
                }
                @SuppressWarnings("unchecked")
                List<DownloadTask> list = (List<DownloadTask>)result;
                if(expected.length != list.size()){
                    return false;
                }
                for(int i = 0; i < list.size(); i++){
                    if(!expected[i].equals(list.get(i))){
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("list should be: ").appendValue(expected);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendText("was").appendValue(item);
            }
        };
    }
}