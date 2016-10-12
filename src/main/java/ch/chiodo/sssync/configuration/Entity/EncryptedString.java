package ch.chiodo.sssync.configuration.Entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@XmlRootElement(name = "password")
public class EncryptedString {
    private byte[] initVector;
    private byte[] cipherText;

    @XmlAttribute(name = "iv")
    public byte[] getInitVector() {
        return initVector;
    }

    public void setInitVector(byte[] initVector) {
        this.initVector = initVector;
    }
    @XmlValue
    public byte[] getCipherText() {
        return cipherText;
    }

    public void setCipherText(byte[] cipherText) {
        this.cipherText = cipherText;
    }
}
