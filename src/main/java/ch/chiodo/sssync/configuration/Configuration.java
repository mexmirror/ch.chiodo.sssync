package ch.chiodo.sssync.configuration;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class Configuration {

    @XmlElementWrapper(name = "modules")
    @XmlElement(name = "module")
    private List<Module> moduleList;

    public Configuration() {
        moduleList = new ArrayList<>();
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }


    public void addModule(Module m){
        moduleList.add(m);
    }

}
