package ch.chiodo.sssync.sync.file;

import java.io.*;

public class LocalTransferFile implements TransferFile {
    private File file;

    public LocalTransferFile(File file) {
        this.file = file;
    }

    @Override
    public boolean delete() {
        return file.delete();
    }

    @Override
    public InputStream createInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public OutputStream createOutputStream() throws FileNotFoundException {
        return new FileOutputStream(file);
    }
}
