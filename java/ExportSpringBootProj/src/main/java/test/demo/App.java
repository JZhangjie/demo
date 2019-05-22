package test.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import test.demo.entityxml.Project;
/**
 * 自动生成java文件
 *
 */
public class App 
{
    public static void main( String[] args )
    {

		Project project = ProjectXMLReader.getProjectFromXml(null);
		ProjectBuilder builder = new ProjectBuilder();
		builder.build("",project);

    }
}
