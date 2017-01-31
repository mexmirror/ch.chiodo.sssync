package ch.chiodo.sssync.sync.file;

import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface TransferFile {
    boolean delete() throws IOException;
    InputStream createInputStream() throws IOException;
    OutputStream createOutputStream() throws IOException;
    String getFilePath();
    boolean exists();
    String getName();
    boolean fileIsSame(TransferFile other);
    DateTime getLastModified();
}
