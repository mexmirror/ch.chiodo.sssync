package ch.chiodo.sssync.sync.file;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static ch.chiodo.sssync.sync.file.FileDownloadHelper.DATE_FORMAT_PATTERN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileDownloadHelperTest {
    @Test
    public void getFilenameWithDate() throws Exception {
        String path = "/home/user/ch.chiodo.java/file.txt";
        DateTime now = DateTime.now();
        String result = FileDownloadHelper.getFilenameWithDate(path);
        String excpected = "/home/user/ch.chiodo.java/file_" + now.toString(DateTimeFormat.forPattern(DATE_FORMAT_PATTERN)) + ".txt";
        assertThat(excpected, is(result));
    }

    @Test
    public void fileContentEquals() throws Exception {
        DateTime timestampOne = DateTime.parse("2011-12-03T10:15:30", DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        DateTime timestampTwo = DateTime.parse("2011-12-03T10:15:45", DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        TransferFile source = mock(TransferFile.class);
        TransferFile destination = mock(TransferFile.class);
        when(source.getLastModified()).thenReturn(timestampOne);
        when(destination.getLastModified()).thenReturn(timestampTwo);
        String file = "File mock";
        ByteArrayInputStream inputStreamOne = new ByteArrayInputStream(file.getBytes());
        ByteArrayInputStream inputStreamTwo = new ByteArrayInputStream(file.getBytes());
        when(source.createInputStream()).thenReturn(inputStreamOne);
        when(destination.createInputStream()).thenReturn(inputStreamTwo);
        boolean result = FileDownloadHelper.equalsTransferFile(source, destination);
        assertThat(result, is(true));
    }

    @Test
    public void fileEqualsTimestampEquals() throws Exception {
        DateTime timestampOne = DateTime.parse("2011-12-03T10:15:30", DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        DateTime timestampTwo = DateTime.parse("2011-12-03T10:15:30", DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        TransferFile source = mock(TransferFile.class);
        TransferFile destination = mock(TransferFile.class);
        when(source.getLastModified()).thenReturn(timestampOne);
        when(destination.getLastModified()).thenReturn(timestampTwo);
        boolean result = FileDownloadHelper.equalsTransferFile(source, destination);
        verify(source, never()).createInputStream();
        verify(destination, never()).createInputStream();
        assertThat(result, is(true));
    }

    @Test
    public void fileIsNotEqual() throws Exception {
        DateTime timestampOne = DateTime.parse("2011-12-03T10:15:30", DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        DateTime timestampTwo = DateTime.parse("2011-12-03T10:15:45", DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        TransferFile source = mock(TransferFile.class);
        TransferFile destination = mock(TransferFile.class);
        when(source.getLastModified()).thenReturn(timestampOne);
        when(destination.getLastModified()).thenReturn(timestampTwo);
        ByteArrayInputStream inputStreamOne = new ByteArrayInputStream("File mock1".getBytes());
        ByteArrayInputStream inputStreamTwo = new ByteArrayInputStream("File mock2".getBytes());
        when(source.createInputStream()).thenReturn(inputStreamOne);
        when(destination.createInputStream()).thenReturn(inputStreamTwo);
        boolean result = FileDownloadHelper.equalsTransferFile(source, destination);
        assertThat(result, is(false));
    }

    @Test(expected = NetworkException.class)
    public void throwsException() throws Exception {
        DateTime timestampOne = DateTime.parse("2011-12-03T10:15:30", DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        DateTime timestampTwo = DateTime.parse("2011-12-03T10:15:45", DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        TransferFile source = mock(TransferFile.class);
        TransferFile destination = mock(TransferFile.class);
        when(source.getLastModified()).thenReturn(timestampOne);
        when(destination.getLastModified()).thenReturn(timestampTwo);
        when(source.createInputStream()).thenReturn(null);
        when(destination.createInputStream()).thenReturn(null);
        boolean result = FileDownloadHelper.equalsTransferFile(source, destination);
    }
}