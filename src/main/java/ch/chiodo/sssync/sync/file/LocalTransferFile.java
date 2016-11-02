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

    @Override
    public String getFilePath() {
        return file.getAbsolutePath();
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public String getName() {
        return file.getName();
    }
}
