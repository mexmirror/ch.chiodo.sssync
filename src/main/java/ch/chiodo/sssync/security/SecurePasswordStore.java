package ch.chiodo.sssync.security;

import ch.chiodo.sssync.configuration.Entity.EncryptedString;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

/**
 * This class is a temporary solution for an encrypted store to securely encrypt and save passwords
 * Source: http://stackoverflow.com/questions/992019/java-256-bit-aes-password-based-encryption/992413
 */
public class SecurePasswordStore {
    private static final String PBKDF_2_WITH_HMAC_SHA_256 = "PBKDF2WithHmacSHA256";
    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private SecretKey secret;

    public SecurePasswordStore(KeySpec spec) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF_2_WITH_HMAC_SHA_256);
        SecretKey tmp = factory.generateSecret(spec);
        secret = new SecretKeySpec(tmp.getEncoded(), AES);
    }

    public EncryptedString encrypt(String sensitiveString) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] cipherText = cipher.doFinal(sensitiveString.getBytes());
        EncryptedString encryptedString = new EncryptedString();
        encryptedString.setCipherText(cipherText);
        encryptedString.setInitVector(iv);
        return encryptedString;
    }

    public String decrypt(EncryptedString encryptedString) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(encryptedString.getInitVector()));
        return new String(cipher.doFinal(encryptedString.getCipherText()));
    }

}
