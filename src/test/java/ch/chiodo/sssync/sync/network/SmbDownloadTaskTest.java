package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.sync.file.FileOperationStrategy;
import ch.chiodo.sssync.sync.file.LocalTransferFile;
import ch.chiodo.sssync.sync.file.SmbTransferFile;
import ch.chiodo.sssync.sync.file.TransferFile;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;

public class SmbDownloadTaskTest {
    @Test
    public void startTransferFileNotExists() throws Exception {
        SmbDownloadTask t = new SmbDownloadTask(null, null, new NullObserver());
        TransferFile source = mock(TransferFile.class);
        TransferFile destination = mock(TransferFile.class);
        when(destination.exists()).thenReturn(false);
        FileOperationStrategy ops = mock(FileOperationStrategy.class);
        t.startTransfer(ops, source, destination);
        verify(ops, times(1)).copyFile(source, destination);
        verify(ops, never()).moveFile(source, destination);
    }

    @Test
    public void startTransferFileExists() throws Exception {
        SmbDownloadTask t = new SmbDownloadTask(null, null, new NullObserver());
        TransferFile source = mock(SmbTransferFile.class);
        TransferFile destination = mock(TransferFile.class);
        when(destination.fileIsSame(destination)).thenReturn(false);
        when(destination.exists()).thenReturn(true);
        when(destination.getFilePath()).thenReturn("/tmp/bla/bla.txt");
        FileOperationStrategy ops = mock(FileOperationStrategy.class);
        t.startTransfer(ops, source, destination);
        verify(ops, times(1)).copyFile(source, destination);
        verify(ops, times(1)).moveFile(any(TransferFile.class), any(LocalTransferFile.class));
    }

    @Test
    public void identicalFiles() throws Exception {
        SmbDownloadTask t = new SmbDownloadTask(null, null, new NullObserver());
        TransferFile source = mock(SmbTransferFile.class);
        TransferFile destination = mock(TransferFile.class);
        when(source.fileIsSame(destination)).thenReturn(true);
        FileOperationStrategy ops = mock(FileOperationStrategy.class);
        t.startTransfer(ops, source, destination);
        verify(ops, never()).copyFile(source, destination);
        verify(ops, never()).moveFile(any(TransferFile.class), any(TransferFile.class));
    }
}