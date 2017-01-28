package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.configuration.entity.EncryptedString;
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
import java.util.ArrayDeque;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

public class SmbDownload extends Observable implements Download {
    private final Observer observer;
    private final Queue<DownloadTask> downloadQueue;
    private String username;
    private SecurePasswordStore keyStore;
    private String domain;


    public SmbDownload(String username, String domain, SecurePasswordStore keyStore) {
        this(username, domain, keyStore, new NullObserver());
    }

    public SmbDownload(String username, String domain, SecurePasswordStore keyStore, Observer observer) {
        this(username, domain, keyStore, observer, new ArrayDeque<>());
    }

    public SmbDownload(String username, String domain, SecurePasswordStore keyStore, Observer observer, Queue<DownloadTask> downloadQueue) {
        this.username = username;
        this.domain = domain;
        this.keyStore = keyStore;
        this.observer = observer;
        this.downloadQueue = downloadQueue;
    }

    @Override
    public void startDownload(String source, String destination, EncryptedString password) throws DownloadException, KeyStoreException {
        try {
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, username, keyStore.decrypt(password));
            SmbFile root = new SmbFile(source, auth);
            downloadFile(root, destination);
        } catch (SmbException | MalformedURLException | UnknownHostException ex) {
            throw new DownloadException(ex);
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | UnsupportedEncodingException ex) {
            throw new KeyStoreException(ex);
        }
    }

    private void downloadFile(SmbFile root, String destinationFolder) throws SmbException, MalformedURLException, UnknownHostException {
        if(root.isDirectory()) {
            SmbFile[] files = root.listFiles();
            for(SmbFile f: files){
                downloadFile(f, destinationFolder);
            }
        } else {
            TransferFile sourceFile = new SmbTransferFile(root);
            String destination = destinationFolder + sourceFile.getName();
            TransferFile destinationFile = new LocalTransferFile(new File(destination));
            downloadQueue.add(new SmbDownloadTask(sourceFile, destinationFile, observer));
            setChanged();
            notifyObservers();
        }
    }
}
