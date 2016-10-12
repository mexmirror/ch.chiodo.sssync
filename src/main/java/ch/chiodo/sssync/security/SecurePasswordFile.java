package ch.chiodo.sssync.security;

import java.io.Serializable;

public class SecurePasswordFile implements Serializable {
    public static final String SECURE_PASSWORD_FILE = "secureFile.bin";
    private byte[] salt;

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
