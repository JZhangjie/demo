
package com.util.auto.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="config")
@XmlAccessorType(XmlAccessType.FIELD)
public class Config {
    @XmlElement(name="vmroot")
    protected String vmroot;
    @XmlElement(name="xmlpath")
    protected String xmlpath;
    @XmlElementWrapper(name="vmconfigs")
    @XmlElement(name="vmconfig")
    protected List<VmConfig> vmconfigs;

    public String getVmroot() {
        return vmroot;
    }

    public void setVmroot(String vmroot) {
        this.vmroot = vmroot;
    }

    public String getXmlpath() {
        return xmlpath;
    }

    public void setXmlpath(String xmlpath) {
        this.xmlpath = xmlpath;
    }

    public List<VmConfig> getVmconfigs() {
        return vmconfigs;
    }

    public void setVmconfigs(List<VmConfig> vmconfigs) {
        this.vmconfigs = vmconfigs;
    }
}
