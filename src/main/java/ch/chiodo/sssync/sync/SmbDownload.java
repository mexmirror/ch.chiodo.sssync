package ch.chiodo.sssync.sync;

import ch.chiodo.sssync.configuration.Entity.EncryptedString;
import ch.chiodo.sssync.security.SecurePasswordStore;
import jcifs.smb.*;
import org.apache.commons.io.IOUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.security.*;
import java.util.Observable;

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
        queue.enqueue(new SmbDownloadTask(root, new File(destination)));
    }

    public class SmbDownloadTask extends Observable implements DownloadTask, Runnable{
        private SmbFile source;
        private File destination;

        public SmbDownloadTask(SmbFile source, File destination) {
            this.source = source;
            this.destination = destination;
        }

        @Override
        public void run() {
            try (SmbFileInputStream inputStream = new SmbFileInputStream(source)){
                try(FileOutputStream fos = new FileOutputStream(destination)) {
                    IOUtils.copy(inputStream, fos);
                }
            } catch (IOException e) {
                DownloadException arg = new DownloadException(String.format("%s", source.getName()), e);
                notifyObservers(arg);
            }

        }
    }
}
