
package com.util.auto.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
public class VmConfig {

    @XmlElement(name="vmpath")
    protected String vmpath;
    @XmlElement(name="vmname")
    protected String vmname;
    @XmlElement(name="outfile")
    protected String outfile;
    @XmlElement(name="type")
    protected String type;
    @XmlElement(name="isdir")
    protected boolean isdir;
    @XmlElement(name="issingle")
    protected boolean issingle;    //true:会替换掉以文件形式存在的
    private List<VmConfig> children;

    public String getVmpath() {
        return vmpath;
    }

    public void setVmpath(String vmpath) {
        this.vmpath = vmpath;
    }

    public String getOutfile() {
        return outfile;
    }

    public void setOutfile(String outfile) {
        this.outfile = outfile;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIsdir() {
        return isdir;
    }

    public void setIsdir(boolean isdir) {
        this.isdir = isdir;
    }

    public String getVmname() {
        return vmname;
    }

    public void setVmname(String vmname) {
        this.vmname = vmname;
    }

    public boolean isIssingle() {
        return issingle;
    }

    public void setIssingle(boolean issingle) {
        this.issingle = issingle;
    }

    public List<VmConfig> getChildren() {
        return children;
    }

    public void setChildren(List<VmConfig> children) {
        this.children = children;
    }
}
