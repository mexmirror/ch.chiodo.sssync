package ch.chiodo.sssync.security;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class EncryptedString  {
    private byte[] initVector;
    private byte[] cipherText;

    public byte[] getInitVector() {
        return initVector;
    }

    public void setInitVector(byte[] initVector) {
        this.initVector = initVector;
    }

    public byte[] getCipherText() {
        return cipherText;
    }

    public void setCipherText(byte[] cipherText) {
        this.cipherText = cipherText;
    }
}
