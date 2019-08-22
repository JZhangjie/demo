package test.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import test.demo.entityxml.Entity;
import test.demo.entityxml.Project;

public class ProjectBuilder {
	String templateDir = System.getProperty("user.dir")+"\\src\\template";
	private String template="";
	private String rootPath="";
	private Project project;
	
	public ProjectBuilder() {
	}
	
	public ProjectBuilder(String templateDir) {
		this.templateDir = templateDir;
	}
	
	public boolean build(String template,Project project) {
		
		//项目构建
		//1. 新建文件夹
		//2. 新建项目相关文件
		//3. 新建业务相关类
		System.out.println( "template位置"+templateDir);
		this.rootPath = project.getProjectpath();
		this.project = project;
		this.buildProject();
		return true;
	}
	
	protected void buildProject() {
		//根目录
		File pFile = new File(this.rootPath);
		if(!pFile.exists()) {
			pFile.mkdirs();
		}

		this.buildFiles();
		
		//构造业务类
		List<Entity> entities= this.project.getEntities();
		EntityBuilder entityBuilder = new EntityBuilder(this.templateDir);
		if(entities!=null && entities.size()>0){
			for (Entity entity:entities) {
				entityBuilder.build(project,entity);
			}
		}
	}
	
	private void buildFiles() {
		System.out.println( "开始项目文件新建>>>"+this.project.getName());
        Properties pro = new Properties();
        pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        pro.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templateDir);
        VelocityEngine velocityEngine = new VelocityEngine(pro);
        velocityEngine.init(); 
        
        VelocityContext ctx = buildContext(this.project);
        String packagepath = this.buildPath(this.rootPath+"/src/main/java",this.project.getPackagename());
        
        Template actionTpt = velocityEngine.getTemplate("pom.xml.vm","UTF-8"); 
        Template actionTpt2 = velocityEngine.getTemplate("application.properties.vm","UTF-8"); 
        Template actionTpt3 = velocityEngine.getTemplate("SpringBootStartApplication.java.vm","UTF-8"); 
        Template actionTpt4 = velocityEngine.getTemplate("TomcatStartApplication.java.vm","UTF-8"); 
        Template actionTpt5 = velocityEngine.getTemplate("MybatisConf.java.vm","UTF-8"); 
        
        outFile(actionTpt,ctx, makeDir(this.rootPath),                         "pom.xml");
        outFile(actionTpt2,ctx,makeDir(this.rootPath+"/src/main/resources"),   "application.properties"); 
        outFile(actionTpt3,ctx,makeDir(packagepath),                           "SpringBootStartApplication.java"); 
        outFile(actionTpt4,ctx,makeDir(packagepath),                           "TomcatStartApplication.java"); 
        outFile(actionTpt5,ctx,makeDir(packagepath),                           "MybatisConf.java"); 
        System.out.println( "项目文件新建完成>>>"+this.project.getName());
    }
	
	//构造包路径
	private String buildPath(String rootPath,String packagename) {
		if(packagename!=null) {
			String[] pStrings = packagename.split("\\.");
			String temppath = rootPath;
			for (String p : pStrings) {
				temppath = temppath+"/"+p;
			}
			return temppath;
		}
		return rootPath;
	}
	
	/*
	 * 使用Entity构建VelocityContext对象
	 */
	private VelocityContext buildContext(Project project) {
        VelocityContext ctx = new VelocityContext(); 
        ctx.put("packagename", project.getPackagename());
        ctx.put("packagepath", project.getPackagepath()); 
        ctx.put("projectname", project.getName());
        ctx.put("description", project.getDescription());
        ctx.put("packagetype", project.getPackagetype()); 
        ctx.put("dbdriver", project.getDbdriver()); 
        ctx.put("dburl", project.getDburl()); 
        ctx.put("dbusername", project.getDbusername()); 
        ctx.put("dbpassword", project.getDbpassword()); 
        
        return ctx;
	}
	
    private void outFile(Template template, VelocityContext ctx, String path,String filename) { 
    	 PrintWriter writer = null; 
    	 try { 
	    	 writer = new PrintWriter(path+"\\"+filename); 
	    	 template.merge(ctx, writer); 
	    	 writer.flush(); 
    	 } catch (FileNotFoundException e) { 
    		 e.printStackTrace(); 
    	 } finally { 
    		 writer.close(); 
    	 } 
    }
    
    private String makeDir(String path) {
    	File file = new File(path);
    	if(!file.exists()) {
    		file.mkdirs();
    	}
    	return path;
    }
}
