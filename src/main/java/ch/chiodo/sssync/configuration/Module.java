package ch.chiodo.sssync.configuration;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "module")
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

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

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
