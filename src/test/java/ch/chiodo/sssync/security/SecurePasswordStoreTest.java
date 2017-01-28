package ch.chiodo.sssync.security;

import ch.chiodo.sssync.configuration.entity.EncryptedString;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SecurePasswordStoreTest {
    private SecurePasswordStore store;
    @Before
    public void setUp() throws Exception {
        SecurePasswordFactory factory = new SecurePasswordFactory();
        store = factory.createPasswordStore("");
    }

    @Test
    public void crypto() throws Exception {
        String plain = "sensitive";
        EncryptedString encryptedString = store.encrypt(plain);
        String s = new String(encryptedString.getCipherText());
        assertThat(s, not(plain));
        String decrypted = store.decrypt(encryptedString);
        assertThat(decrypted, is(plain));
    }

}
