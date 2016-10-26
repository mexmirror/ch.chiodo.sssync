package ch.chiodo.sssync.sync.network;

public class DownloadException extends Exception {
    public DownloadException(Exception cause) {
        super(cause);
    }

    public DownloadException(String message, Exception cause){
        super(message, cause);
    }
}
