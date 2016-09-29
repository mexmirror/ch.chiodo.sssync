package ch.chiodo.sssync.sync;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

public interface Download {
    void StartDownload(String source, String destination) throws DownloadException, MalformedURLException, UnknownHostException;
}
