package ch.chiodo.sssync.configuration.Entity;

import ch.chiodo.sssync.configuration.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "module")
@XmlType(propOrder = {"name", "sourcePath", "destinationPath", "lastSync"})
public class Module {

    private String name;
    private String sourcePath;
    private String destinationPath;
    private DateTime lastSync;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "source")
    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @XmlElement(name = "destination")
    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    @XmlJavaTypeAdapter(type=DateTime.class, value=DateTimeAdapter.class)
    public DateTime getLastSync() {
        return lastSync;
    }

    public void setLastSync(DateTime lastSync) {
        this.lastSync = lastSync;
    }
}
