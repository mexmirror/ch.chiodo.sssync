package ch.chiodo.sssync.sync;

import ch.chiodo.sssync.configuration.Entity.EncryptedString;

import java.security.KeyStoreException;

public interface Download {
    void StartDownload(String source, String destination, EncryptedString password) throws DownloadException, KeyStoreException;
}
