package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.configuration.Entity.EncryptedString;

import java.security.KeyStoreException;

public interface Download {
    void StartDownload(String source, String destination, EncryptedString password) throws DownloadException, KeyStoreException;
}
