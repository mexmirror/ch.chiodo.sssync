package ch.chiodo.sssync.configuration;

import ch.chiodo.sssync.sync.EncryptedString;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="user")
@XmlType(propOrder = {"username", "domainName", "password"})
public class User {
    private String username;
    private String domainName;
    private EncryptedString password;

    public User() {
        password = new EncryptedString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getPassword() {
        return password.getString();
    }

    public void setPassword(String password) {
        this.password.setString(password);
    }
}
