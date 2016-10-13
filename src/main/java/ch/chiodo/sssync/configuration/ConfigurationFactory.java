package ch.chiodo.sssync.configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class ConfigurationFactory {

    private static final String MODULE_CONFIG_FILE = "config.xml";

    public Configuration buildConfiguration() throws JAXBException, FileNotFoundException{
        return buildConfigurationImpl(new FileInputStream(new File(MODULE_CONFIG_FILE)));
    }

    Configuration buildConfigurationImpl(InputStream file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object o = unmarshaller.unmarshal(file);
        if(o instanceof Configuration) {
            return (Configuration)o;
        }
        throw new ClassMismatchException("Configuration could not be unmarshalled. Types do not match");
    }

    public void saveConfiguration(Configuration configuration) throws JAXBException, FileNotFoundException{
        saveConfigurationImpl(configuration, new FileOutputStream(new File(MODULE_CONFIG_FILE)));
    }

    void saveConfigurationImpl(Configuration configuration, OutputStream file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(configuration, file);
    }
}
