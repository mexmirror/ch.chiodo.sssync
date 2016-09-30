package ch.chiodo.sssync.configuration;

import org.joda.time.DateTime;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class ConfigurationFactoryTest {

    @Test
    public void testMarshallConfiguration() throws Exception {
        ConfigurationFactory factory = new ConfigurationFactory();
        Configuration configuration = new Configuration();
        Module m = new Module();
        m.setName("module1");
        m.setSourcePath("smb://path/to/source");
        m.setDestinationPath("/path/to/dest");
        DateTime date1 = DateTime.now().minusHours(2).minusMinutes(12);
        m.setLastSync(date1);
        configuration.addModule(m);
        m = new Module();
        m.setName("module2");
        m.setSourcePath("smb://path/to/source");
        m.setDestinationPath("/path/to/dest");
        DateTime date2 = DateTime.now().minusDays(1).minusHours(16).minusMinutes(36);
        m.setLastSync(date2);
        configuration.addModule(m);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        factory.saveConfigurationImpl(configuration, baos);
        String expected =
                "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                "<configuration>\n" +
                "    <modules>\n" +
                "        <module>\n" +
                "            <name>module1</name>\n" +
                "            <source>smb://path/to/source</source>\n" +
                "            <destination>/path/to/dest</destination>\n" +
                "            <lastSync>" + date1.toString() + "</lastSync>\n" +
                "        </module>\n" +
                "        <module>\n" +
                "            <name>module2</name>\n" +
                "            <source>smb://path/to/source</source>\n" +
                "            <destination>/path/to/dest</destination>\n" +
                "            <lastSync>" + date2.toString() +"</lastSync>\n" +
                "        </module>\n" +
                "    </modules>\n" +
                "</configuration>\n";
        assertEquals(expected, baos.toString());
    }

    @Test
    public void testUnmarshallConfiguration() throws Exception {
        String xml =
                "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                        "<configuration>\n" +
                        "    <modules>\n" +
                        "        <module>\n" +
                        "            <name>module1</name>\n" +
                        "            <source>smb://path/to/source</source>\n" +
                        "            <destination>/path/to/dest</destination>\n" +
                        "            <lastSync>2016-09-28T12:39:04.539+02:00</lastSync>\n" +
                        "        </module>\n" +
                        "        <module>\n" +
                        "            <name>module2</name>\n" +
                        "            <source>smb://path/to/source</source>\n" +
                        "            <destination>/path/to/dest</destination>\n" +
                        "            <lastSync>2016-09-25T10:15:04.539+02:00</lastSync>\n" +
                        "        </module>\n" +
                        "    </modules>\n" +
                        "</configuration>\n";
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        ConfigurationFactory factory = new ConfigurationFactory();
        Configuration configuration = factory.buildConfigurationImpl(bais);
        assertEquals(2, configuration.getModuleList().size());
        Module m1 = configuration.getModuleList().get(0);
        assertEquals("module1", m1.getName());
        assertEquals("smb://path/to/source", m1.getSourcePath());
        assertEquals("/path/to/dest", m1.getDestinationPath());
        assertEquals(new DateTime("2016-09-28T12:39:04.539+02:00"), m1.getLastSync());
    }
}
