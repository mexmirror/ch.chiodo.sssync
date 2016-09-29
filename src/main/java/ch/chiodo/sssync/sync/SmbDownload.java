package ch.chiodo.sssync.sync;

import jcifs.smb.*;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class SmbDownload implements Download {
    private String username;
    private EncryptedString password;
    private String domain;
    private AtomicInteger count = new AtomicInteger(0);
    private Queue<SmbDownloadTask> downloadQueue = new LinkedBlockingDeque<>();

    public SmbDownload(String username, EncryptedString password, String domain) {
        this.username = username;
        this.password = password;
        this.domain = domain;
    }

    @Override
    public void StartDownload(String source, String destination) throws DownloadException, MalformedURLException, UnknownHostException {
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, username, password.getString());
        SmbFile root = new SmbFile(source, auth);
        try {
            DownloadFile(root, destination);
        } catch (SmbException ex){
            throw new DownloadException(ex);
        }


    }
    public int getCount() {
        return count.get();
    }

    private void DownloadFile(SmbFile root, String destination) throws SmbException, MalformedURLException, UnknownHostException {
        if(root.isDirectory()) {
            SmbFile[] files = root.listFiles();
            for(SmbFile f: files){
                DownloadFile(f, destination);
            }
        }
        count.getAndIncrement();
        root.copyTo(new SmbFile(destination));
    }
}
