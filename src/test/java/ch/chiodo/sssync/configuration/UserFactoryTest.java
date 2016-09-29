package ch.chiodo.sssync.configuration;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserFactoryTest {

    @Test
    public void testMarshallUser() {
        UserFactory factory = new UserFactory();
        User user = new User();
        user.setUsername("hans");
        user.setPassword("peter");
        user.setDomainName("company.net");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            factory.saveUserImpl(user, baos);
        } catch (JAXBException ex) {
            fail(ex.getStackTrace().toString());
        }
        String excpected =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<user>\n" +
                "    <username>hans</username>\n" +
                "    <domainName>company.net</domainName>\n" +
                "    <password>peter</password>\n" +
                "</user>\n";
        assertEquals(excpected, baos.toString());
    }

    @Test
    public void testUnmarshallUser() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<user>\n" +
                        "    <username>hans</username>\n" +
                        "    <domainName>company.net</domainName>\n" +
                        "    <password>peter</password>\n" +
                        "</user>\n";
        UserFactory factory = new UserFactory();
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        User user = null;
        try {
            user = factory.buildUserImpl(bais);
        } catch (JAXBException ex) {
            fail(ex.getStackTrace().toString());
        }
        assertEquals("hans", user.getUsername());
        assertEquals("peter", user.getPassword());
        assertEquals("company.net", user.getDomainName());
    }
}
