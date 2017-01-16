package ch.chiodo.sssync.security;

import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class SecurePasswordFactory {
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 128;
    private SecurePasswordFile passwordFile;

    public SecurePasswordStore createPasswordStore(String masterPassword) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        File file = new File(SecurePasswordFile.SECURE_PASSWORD_FILE);
        if(file.exists()){
            passwordFile = loadPasswordFile();
        } else {
            passwordFile = createPasswordFile();
        }
        KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), passwordFile.getSalt(), ITERATION_COUNT, KEY_LENGTH);
        return new SecurePasswordStore(spec);
    }

    private static SecurePasswordFile createPasswordFile() {
        SecurePasswordFile passwordFile = new SecurePasswordFile();
        passwordFile.setSalt(new SecureRandom().generateSeed(8));
        return passwordFile;
    }

    private static SecurePasswordFile loadPasswordFile() throws IOException, ClassNotFoundException {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SecurePasswordFile.SECURE_PASSWORD_FILE))) {
            return (SecurePasswordFile)inputStream.readObject();
        }
    }

    public void savePasswordStore() throws IOException {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SecurePasswordFile.SECURE_PASSWORD_FILE))) {
            outputStream.writeObject(passwordFile);
        }
    }
}
