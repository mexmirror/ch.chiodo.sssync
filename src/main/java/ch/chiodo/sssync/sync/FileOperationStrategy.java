package ch.chiodo.sssync.sync;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileOperationStrategy {
    private static final long TWO_GIGABYTE = 2147483548L;
    private CopyStrategy copyStrategy;

    public void moveFile(File source, File destination) throws IOException {
        copyFile(new FileInputStream(source), new FileOutputStream(destination));
        source.delete();
    }

    public void copyFile(File source, File destination) throws IOException {
        copyFile(new FileInputStream(source), new FileOutputStream(destination));
    }

    void copyFile(InputStream is, OutputStream os) throws IOException {
        if(is.available() > TWO_GIGABYTE) {
            IOUtils.copyLarge(is, os);
        } else {
            IOUtils.copy(is, os);
        }
    }
}
