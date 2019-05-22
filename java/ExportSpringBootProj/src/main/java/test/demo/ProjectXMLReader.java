package test.demo;

import test.demo.entityxml.Entity;
import test.demo.entityxml.Project;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class ProjectXMLReader {

    public static Project getProjectFromXml(String xmlpath) {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance("test.demo.entityxml");
            Unmarshaller u = jc.createUnmarshaller();
            String xmlFilePath = System.getProperty("user.dir")+"\\src\\xml\\project.xsd.xml";
            if(xmlpath != null && !xmlpath.trim().equals("")){
                xmlFilePath = xmlFilePath;
            }
            System.out.println("使用的xml文件为:"+xmlFilePath);
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
