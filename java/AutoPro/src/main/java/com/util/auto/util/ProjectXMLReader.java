package com.util.auto.util;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.util.auto.model.Project;

public class ProjectXMLReader {

    public static Project getProjectFromXml(String xmlpath) {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance("com.util.auto.model");
            Unmarshaller u = jc.createUnmarshaller();
            String xmlFilePath = System.getProperty("user.dir")+"\\src\\main\\resources\\demo\\model.xml";
            if(xmlpath != null && !xmlpath.trim().equals("")){
                xmlFilePath = xmlpath;
            }
            System.out.println("模板库:"+xmlFilePath);
            Project project = (Project) u.unmarshal(new File(xmlFilePath));
            return project;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static void testXmlToObj() {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance("test.demo.entityxml");
            Unmarshaller u = jc.createUnmarshaller();
            String xmlFilePath = System.getProperty("user.dir")+"\\src\\xml\\project.xsd.xml";
            System.out.println(xmlFilePath);
            Project project = (Project) u.unmarshal(new File(xmlFilePath));
            System.out.println(project);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
