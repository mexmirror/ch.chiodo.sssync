package ch.chiodo.sssync.sync.file;

import jcifs.smb.SmbException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface TransferFile {
    boolean delete() throws IOException;
    InputStream createInputStream() throws IOException;
    OutputStream createOutputStream() throws IOException;
}
