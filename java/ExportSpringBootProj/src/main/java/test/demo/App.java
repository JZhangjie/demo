package test.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import test.demo.entityxml.Entity;
import test.demo.entityxml.Project;
/**
 * 自动生成java文件
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String templateDir = System.getProperty("user.dir")+"\\src\\template\\t2";
    	String xmlpath = System.getProperty("user.dir")+"\\src\\xml\\wfjsjg_p1.xml";
    	
    	workOnXmlAndDB( templateDir, xmlpath);
    }
    
    public static void workOnlyXml(String templateDir,String xmlpath) {
		Project project = ProjectXMLReader.getProjectFromXml(xmlpath);
		ProjectBuilder builder = new ProjectBuilder(templateDir);
		builder.build("",project);
    }
    
    public static void workOnXmlAndDB(String templateDir,String xmlpath) {
		Project project = ProjectXMLReader.getProjectFromXml(xmlpath);
		List<Entity> entities = project.getEntities();
		if(entities==null) {
			entities = new ArrayList<Entity>();
			project.setEntities(entities);
		}
		List<String> tablenams = project.getTablelist();
		List<String> ntables = project.getIgnoretablelist();
		DatabaseUtil.setParams(project);
		List<String> tablesfromdb  = DatabaseUtil.getTableNames();
		if(tablenams==null) {
			tablenams = tablesfromdb;
		}
		for (String table : tablenams) {
			if(ntables.contains(table)) {
				continue;
			}
			Entity entity = new Entity();
			entity.setTable(table);
			int index = entities.indexOf(entity);
			if(index!=-1) {
				entity = entities.get(index);
				DatabaseUtil.getEntityFromTable(table, entity);
			}else {
				entity = DatabaseUtil.getEntityFromTable(table, null);
				entities.add(entity);
			}
		}
		
		ProjectBuilder builder = new ProjectBuilder(templateDir);
		builder.build("",project);
    }
}
