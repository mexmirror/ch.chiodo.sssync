package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.configuration.Entity.EncryptedString;
import ch.chiodo.sssync.security.SecurePasswordStore;
import ch.chiodo.sssync.sync.file.*;
import jcifs.smb.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.security.*;
import java.util.Deque;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SmbDownload implements Download {
    private final Observer observer;
    private String username;
    private SecurePasswordStore keyStore;
    private String domain;
    private Deque<DownloadTask> queue;

    public SmbDownload(String username, String domain, SecurePasswordStore keyStore) {
        this(username, domain, keyStore, new NullObserver());
    }
    public SmbDownload(String username, String domain, SecurePasswordStore keyStore, Observer observer) {
        this(username, domain, keyStore, new ConcurrentLinkedDeque<>(), observer);
    }

    public SmbDownload(String username, String domain, SecurePasswordStore keyStore, Deque<DownloadTask> queue, Observer observer) {
        this.username = username;
        this.domain = domain;
        this.keyStore = keyStore;
        this.queue = queue;
        this.observer = observer;
    }

    @Override
    public void StartDownload(String source, String destination, EncryptedString password) throws DownloadException, KeyStoreException {
        try {
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, username, keyStore.decrypt(password));
            SmbFile root = new SmbFile(source, auth);
            DownloadFile(root, destination);
        } catch (SmbException |
                MalformedURLException |
                UnknownHostException ex){
            throw new DownloadException(ex);
        } catch (NoSuchPaddingException |
                InvalidKeyException |
                NoSuchAlgorithmException |
                IllegalBlockSizeException |
                BadPaddingException |
                InvalidAlgorithmParameterException |
                UnsupportedEncodingException ex) {
            throw new KeyStoreException(ex);
        }
    }

    private void DownloadFile(SmbFile root, String destinationFolder) throws SmbException, MalformedURLException, UnknownHostException {
        if(root.isDirectory()) {
            SmbFile[] files = root.listFiles();
            for(SmbFile f: files){
                DownloadFile(f, destinationFolder);
            }
        }
        TransferFile sourceFile = new SmbTransferFile(root);
        String destination = destinationFolder + sourceFile.getName();
        TransferFile destinationFile = new LocalTransferFile(new File(destination));
        queue.addLast(new SmbDownloadTask(sourceFile, destinationFile, observer));
    }

    public Deque<DownloadTask> getQueue() {
        return queue;
    }
}
