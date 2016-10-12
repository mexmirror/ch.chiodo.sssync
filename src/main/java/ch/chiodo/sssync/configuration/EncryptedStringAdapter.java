package ch.chiodo.sssync.configuration;

import ch.chiodo.sssync.security.EncryptedString;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class EncryptedStringAdapter extends XmlAdapter<String, EncryptedString>{

    @Override
    public EncryptedString unmarshal(String v) throws Exception {
        return null;
    }

    @Override
    public String marshal(EncryptedString v) throws Exception {
        return null;
    }
}
