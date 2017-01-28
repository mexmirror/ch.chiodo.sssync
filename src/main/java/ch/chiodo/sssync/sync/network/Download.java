package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.configuration.entity.EncryptedString;

import java.security.KeyStoreException;

@FunctionalInterface
public interface Download {
    void startDownload(String source, String destination, EncryptedString password) throws DownloadException, KeyStoreException;
}
