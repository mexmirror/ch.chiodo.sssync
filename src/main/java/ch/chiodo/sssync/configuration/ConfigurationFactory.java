package ch.chiodo.sssync.configuration;

import com.sun.corba.se.impl.io.TypeMismatchException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ConfigurationFactory {

    private static final String MODULE_CONFIG_FILE = "config.xml";

    public Configuration buildConfiguration() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object o = unmarshaller.unmarshal(new File(MODULE_CONFIG_FILE));
        if(o instanceof Configuration) {
            return (Configuration)o;
        }
        throw new TypeMismatchException("Configuration could not be unmarshalled. Types do not match");
    }

    public void saveConfiguration(Configuration configuration) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(configuration, new File(MODULE_CONFIG_FILE));
    }
}
