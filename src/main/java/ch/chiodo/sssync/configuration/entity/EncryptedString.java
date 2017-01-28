package ch.chiodo.sssync.configuration.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

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
