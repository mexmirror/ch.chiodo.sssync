package ch.chiodo.sssync.sync.file;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileOperationStrategy {
    private static final long TWO_GIGABYTE = 2147483548L;

    public void moveFile(TransferFile source, TransferFile destination) throws IOException {
        copyFile(source.createInputStream(), destination.createOutputStream());
        source.delete();
    }

    public void copyFile(TransferFile source, TransferFile destination) throws IOException {
        copyFile(source.createInputStream(), destination.createOutputStream());
    }

    private void copyFile(InputStream is, OutputStream os) throws IOException {
        if(is.available() > TWO_GIGABYTE) {
            IOUtils.copyLarge(is, os);
        } else {
            IOUtils.copy(is, os);
        }
    }
}
