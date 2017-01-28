package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.configuration.entity.EncryptedString;
import ch.chiodo.sssync.security.SecurePasswordStore;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class SmbDownloadTest {

    @Test
    public void downloadFile() throws Exception {
        SecurePasswordStore store = mock(SecurePasswordStore.class);
        EncryptedString es = new EncryptedString();
        when(store.decrypt(es)).thenReturn("qwertz");
        String user = "test";
        Download download = new SmbDownload(user, null, store);
        //download.startDownload("smb://ns.wg.int/home/", "./tmp", es);
    }
}
