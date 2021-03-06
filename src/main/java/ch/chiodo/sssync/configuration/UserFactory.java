package ch.chiodo.sssync.configuration;

import ch.chiodo.sssync.configuration.entity.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class UserFactory {

    private static final String USER_CONFIG_FILE = "user.xml";

    public User buildUser() throws JAXBException , FileNotFoundException{
        return buildUserImpl(new FileInputStream(new File(USER_CONFIG_FILE)));
    }

    User buildUserImpl(InputStream file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(User.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object o = unmarshaller.unmarshal(file);
        if(o instanceof User){
            return (User)o;
        }
        throw new ClassMismatchException("User configuration could not be unmarshalled. Types do not match");
    }

    public void saveUser(User user) throws JAXBException, FileNotFoundException {
        saveUserImpl(user, new FileOutputStream(new File(USER_CONFIG_FILE)));
    }

    void saveUserImpl(User user, OutputStream file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(User.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(user, file);
    }
}
