package ch.chiodo.sssync.sync;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class SmbDownloadTask implements Runnable{
    private SmbFile source;
    private SmbFile destination;

    public SmbDownloadTask(SmbFile source, SmbFile destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void run() {
        try {
            source.copyTo(destination);
        } catch (SmbException e) {
            e.printStackTrace();
        }
    }


}
