package ch.chiodo.sssync.configuration.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="user")
@XmlType(propOrder = {"username", "domainName", "encryptedPassword"})
public class User {

    private String username;
    private String domainName;
    private EncryptedString encryptedPassword;

    @XmlElement(required = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement(required = true)
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @XmlElement(name = "password", required = true)
    public EncryptedString getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(EncryptedString encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
