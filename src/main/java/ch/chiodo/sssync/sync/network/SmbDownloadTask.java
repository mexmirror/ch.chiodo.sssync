package ch.chiodo.sssync.sync.network;

import ch.chiodo.sssync.sync.file.*;
import jcifs.smb.SmbFile;

import java.io.File;
import java.io.IOException;
import java.util.Observer;

public class SmbDownloadTask extends DownloadTask {
    private final TransferFile source;
    private final TransferFile destination;

    public SmbDownloadTask(TransferFile source, TransferFile destination, Observer observer) {
        this.source = source;
        this.destination = destination;
        this.addObserver(observer);
    }

    @Override
    public void run() {
        FileOperationStrategy op = new FileOperationStrategy();
        startTransfer(op, source, destination);
    }

    void startTransfer(FileOperationStrategy op, TransferFile sourceFile, TransferFile destinationFile) {
        if(destinationFile.exists()) {
            String originPath = destinationFile.getFilePath();
            String newPath = FileDownloadHelper.getFilenameWithDate(originPath);
            moveFile(op, destinationFile, newPath);
        }
        copyFile(op, sourceFile, destinationFile);
        notifyObservers(sourceFile);
    }

    private void copyFile(FileOperationStrategy op, TransferFile sourceFile, TransferFile destinationFile) {
        try {
            op.copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            handleException(e, "An error occurred while downloading: "+ source.getName());
        }
    }

    private void moveFile(FileOperationStrategy op, TransferFile destinationFile, String newPath) {
        try {
            op.moveFile(destinationFile, new LocalTransferFile(new File(newPath)));
        } catch (IOException e) {
            handleException(e, "An error occurred while moving: " + destination.getName());
        }
    }

    private void handleException(Exception e, String message) {
        DownloadException ex = new DownloadException(message, e);
        notifyObservers(ex);
        Thread.currentThread().interrupt();
    }
}
