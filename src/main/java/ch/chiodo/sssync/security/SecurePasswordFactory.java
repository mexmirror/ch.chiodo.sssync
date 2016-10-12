package ch.chiodo.sssync.security;

import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class SecurePasswordFactory {
    SecurePasswordFile passwordFile;

    public SecurePasswordStore createPasswordStore(String masterPassword) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        File file = new File(SecurePasswordFile.SECURE_PASSWORD_FILE);
        if(file.exists()){
            passwordFile = loadPasswordFile();
        } else {
            passwordFile = createPasswordFile();
        }
        KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), passwordFile.getSalt(), 65536, 128);
        return new SecurePasswordStore(spec);
    }

    private static SecurePasswordFile createPasswordFile() {
        SecurePasswordFile passwordFile = new SecurePasswordFile();
        passwordFile.setSalt(new SecureRandom().generateSeed(8));
        return passwordFile;
    }

    private static SecurePasswordFile loadPasswordFile() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SecurePasswordFile.SECURE_PASSWORD_FILE));
        return (SecurePasswordFile)inputStream.readObject();

    }

    public void savePasswordStore(SecurePasswordStore store) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SecurePasswordFile.SECURE_PASSWORD_FILE));
        outputStream.writeObject(passwordFile);
    }
}
