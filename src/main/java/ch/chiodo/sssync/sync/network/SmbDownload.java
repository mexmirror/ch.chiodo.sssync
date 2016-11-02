package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.configuration.Entity.EncryptedString;
import ch.chiodo.sssync.security.SecurePasswordStore;
import ch.chiodo.sssync.sync.file.*;
import jcifs.smb.*;
import org.apache.commons.io.IOUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.security.*;

public class SmbDownload implements Download {
    private String username;
    private SecurePasswordStore keyStore;
    private String domain;
    private DownloadQueue queue;

    public SmbDownload(String username, String domain, SecurePasswordStore keyStore) {
        this(username, domain, keyStore, new DownloadQueue());
    }

    public SmbDownload(String username, String domain, SecurePasswordStore keyStore, DownloadQueue queue) {
        this.username = username;
        this.domain = domain;
        this.keyStore = keyStore;
        this.queue = queue;
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

    private void DownloadFile(SmbFile root, String destination) throws SmbException, MalformedURLException, UnknownHostException {
        if(root.isDirectory()) {
            SmbFile[] files = root.listFiles();
            for(SmbFile f: files){
                DownloadFile(f, destination);
            }
        }
        TransferFile sourceFile = new SmbTransferFile(root);
        TransferFile destinationFile = new LocalTransferFile(new File(destination));
        queue.enqueue(new SmbDownloadTask(sourceFile, destinationFile));
    }
}
