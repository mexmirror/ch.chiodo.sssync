package ch.chiodo.sssync.configuration;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserFactoryTest {

    @Test
    public void testMarshallUser() throws Exception{
        UserFactory factory = new UserFactory();
        User user = new User();
        user.setUsername("hans");
        user.setEncryptedPassword("peter");
        user.setDomainName("company.net");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        factory.saveUserImpl(user, baos);
        String excpected =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<user>\n" +
                "    <username>hans</username>\n" +
                "    <domainName>company.net</domainName>\n" +
                "    <password>peter</password>\n" +
                "</user>\n";
        assertThat(excpected, is(baos.toString()));
    }

    @Test
    public void testUnmarshallUser() throws Exception{
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<user>\n" +
                        "    <username>hans</username>\n" +
                        "    <domainName>company.net</domainName>\n" +
                        "    <password>peter</password>\n" +
                        "</user>\n";
        UserFactory factory = new UserFactory();
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        User user = factory.buildUserImpl(bais);
        assertThat("hans", is(user.getUsername()));
        assertThat("peter", is(user.getEncryptedPassword()));
        assertThat("company.net", is(user.getDomainName()));
    }

    @Test(expected = JAXBException.class)
    public void testInvalidUser() throws Exception{
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<wrong>\n" +
                        "</wrong>\n";
        UserFactory factory = new UserFactory();
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        factory.buildUserImpl(bais);
    }
}
