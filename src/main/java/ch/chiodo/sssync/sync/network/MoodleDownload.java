package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.configuration.Entity.EncryptedString;

import java.security.KeyStoreException;

public class MoodleDownload implements Download {

    @Override
    public void StartDownload(String source, String destination, EncryptedString password) throws DownloadException, KeyStoreException {

    }

    public class MoodleDownloadTask extends DownloadTask {

        @Override
        public void run() {

        }
    }
}
