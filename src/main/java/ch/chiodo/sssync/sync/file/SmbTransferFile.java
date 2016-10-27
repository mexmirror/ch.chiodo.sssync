package ch.chiodo.sssync.sync.file;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SmbTransferFile implements TransferFile {
    private SmbFile file;

    public SmbTransferFile(SmbFile file) {
        this.file = file;
    }

    @Override
    public boolean delete() throws IOException {
        try {
            file.delete();
        } catch (SmbException ex ){
            throw new IOException(ex);
        }
        return file.exists();
    }

    @Override
    public InputStream createInputStream() throws IOException {
        return file.getInputStream();
    }

    @Override
    public OutputStream createOutputStream() throws IOException {
        return file.getOutputStream();
    }
}