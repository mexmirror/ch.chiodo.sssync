package ch.chiodo.sssync.console;

import ch.chiodo.sssync.configuration.*;
import org.joda.time.DateTime;

import javax.xml.bind.JAXBException;

public class ConsoleEntry {
    public static void main(String[] args) {
        getuser();
        ConfigurationFactory factory = new ConfigurationFactory();
        Configuration c = new Configuration();
        Module m = new Module();
        m.setName("name");
        m.setSourcePath("source");
        m.setDestinationPath("dest");
        m.setLastSync(DateTime.now());
        c.addModule(m);
        m = new Module();
        m.setName("name1");
        m.setSourcePath("source");
        c.addModule(m);
        Configuration c1 = null;
        try{
            //factory.saveConfiguration(c);
            c1 = factory.buildConfiguration();
        } catch (JAXBException ex){
            ex.printStackTrace();
        }
        System.out.println("c1 = " + c1.getModuleList());
    }

    private static void getuser() {
        UserFactory factory = new UserFactory();
        User u = new User();
        u.setUsername("user");
        u.setDomainName("domain");
        u.setPassword("123");
        User u1 = null;
        try {
            //factory.saveUser(u);
            u1 = factory.buildUser();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        u.equals(u1);
    }
}
