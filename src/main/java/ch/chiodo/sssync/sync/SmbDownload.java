package ch.chiodo.sssync.sync;

import ch.chiodo.sssync.configuration.Entity.EncryptedString;
import ch.chiodo.sssync.security.SecurePasswordFactory;
import ch.chiodo.sssync.security.SecurePasswordStore;
import jcifs.smb.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class SmbDownload implements Download {
    private String username;
    private SecurePasswordStore keyStore;
    private String domain;
    private AtomicInteger count = new AtomicInteger(0);

    public SmbDownload(String username, String masterPassword, String domain) throws ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        this.username = username;
        SecurePasswordFactory factory = new SecurePasswordFactory();
        keyStore = factory.createPasswordStore(masterPassword);
        this.domain = domain;
    }

    @Override
    public void StartDownload(String source, String destination, EncryptedString password) throws DownloadException, KeyStoreException {
        try {
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, username, keyStore.decrypt(password));
            SmbFile root = new SmbFile(source, auth);
            DownloadFile(root, destination);
        } catch (SmbException | MalformedURLException | UnknownHostException ex){
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
        count.getAndIncrement();
        root.copyTo(new SmbFile(destination));
    }
}
