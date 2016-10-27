package ch.chiodo.sssync.sync.file;

import ch.chiodo.sssync.sync.file.FileOperationStrategy;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileOperationStrategyTest {
    @Test
    public void copyFile() throws Exception {
        TransferFile source = mock(TransferFile.class);
        TransferFile destination = mock(TransferFile.class);
        String testString = "test file";
        InputStream input = new ByteArrayInputStream(testString.getBytes());
        OutputStream output = new ByteArrayOutputStream();
        when(source.createInputStream()).thenReturn(input);
        when(destination.createOutputStream()).thenReturn(output);
        FileOperationStrategy fos = new FileOperationStrategy();
        fos.copyFile(source, destination);
        assertThat(input.available(), is(0));
        assertThat(output.toString(), is(testString));
    }

    @Test
    public void moveFile() throws Exception {
        TransferFile source = mock(TransferFile.class);
        TransferFile destination = mock(TransferFile.class);
        String test = "file mock";
        InputStream input = new ByteArrayInputStream(test.getBytes());
        OutputStream output = new ByteArrayOutputStream();
        when(source.createInputStream()).thenReturn(input);
        when(destination.createOutputStream()).thenReturn(output);
        FileOperationStrategy fos = new FileOperationStrategy();
        fos.moveFile(source, destination);
        verify(source, times(1)).delete();
        assertThat(input.available(), is(0));
        assertThat(output.toString(), is(test));
    }
}