
package com.util.auto.config;

import com.util.auto.model.Entity;
import com.util.auto.model.Field;
import com.util.auto.model.Function;
import com.util.auto.model.Project;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _Project_QNAME = new QName("http://www.example.org/config", "config");


    public ObjectFactory() {
    }

    public Config createConfig() {
        return new Config();
    }


    public VmConfig createVmConfig() {
        return new VmConfig();
    }

    @XmlElementDecl(namespace = "http://www.example.org/config", name = "config")
    public JAXBElement<Config> createConfig(Config value) {
        return new JAXBElement<Config>(_Project_QNAME, Config.class, null, value);
    }

}
