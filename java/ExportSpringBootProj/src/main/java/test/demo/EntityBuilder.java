package test.demo;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import test.demo.entityxml.Entity;
import test.demo.entityxml.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.lang.model.element.VariableElement;

public  class EntityBuilder {
	String templateDir = System.getProperty("user.dir")+"\\src\\template";
	
	public EntityBuilder(String templateDir) {
		this.templateDir = templateDir;
	}
	
	public boolean build(Project project, Entity entity) {
		buildFiles(project,entity);
		return false;
	}
	
	private void buildFiles(Project project, Entity entity) {
        System.out.println( "开始构建业务类==》"+entity.getName() );
        Properties pro = new Properties();
        pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        pro.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templateDir);
        VelocityEngine velocityEngine = new VelocityEngine(pro);
        velocityEngine.init(); 
        
        VelocityContext ctx = buildContext(project,entity);
        String rootPath = this.buildPath(project);
        
        Template actionTpt = velocityEngine.getTemplate("dao.xml.vm","UTF-8"); 
        Template actionTpt2 = velocityEngine.getTemplate("dao.java.vm","UTF-8"); 
        Template actionTpt3 = velocityEngine.getTemplate("entity.vm","UTF-8"); 
        Template actionTpt4 = velocityEngine.getTemplate("service.vm","UTF-8"); 
        Template actionTpt5 = velocityEngine.getTemplate("endpoint.vm","UTF-8"); 
        
        outFile(actionTpt,ctx, makeDir(rootPath+"/dao/"),     entity.getName()+"Mapper.xml");
        outFile(actionTpt2,ctx,makeDir(rootPath+"/dao/"),     entity.getName()+"Mapper.java");
        outFile(actionTpt3,ctx,makeDir(rootPath+"/entity/"),  entity.getName()+".java");
        outFile(actionTpt4,ctx,makeDir(rootPath+"/service/"), entity.getName()+"Service.java");
        outFile(actionTpt5,ctx,makeDir(rootPath+"/endpoint/"),entity.getName()+"Controller.java");
        System.out.println( "业务类构建完成==》"+entity.getName() );
    }
	
	//构造包路径
	private String buildPath(Project project) {
		String temppath = project.getProjectpath()+"/src/main/java";
		String pname = project.getPackagename();
		String[] pStrings = pname.split("\\.");
		for (String p : pStrings) {
			temppath = temppath+"/"+p;
		}
		return temppath;
	}
	
	/*
	 * 使用Entity构建VelocityContext对象
	 */
	private VelocityContext buildContext(Project project,Entity entity) {
		List<test.demo.entityxml.Field> entity_attrs_forsearch = new ArrayList<test.demo.entityxml.Field>();
		for(int i=0;i<entity.getFields().size();i++) {
			test.demo.entityxml.Field field = entity.getFields().get(i);
			if(field.isSearch()) {
				entity_attrs_forsearch.add(field);
			}
		}
        VelocityContext ctx = new VelocityContext(); 
        ctx.put("root_package_name", project.getPackagename());
        ctx.put("entity_name", entity.getName());
        ctx.put("entity_name_low", entity.getNamelow());
        ctx.put("entity_table", entity.getTable());
        ctx.put("entity_attrs", entity.getFields());
        ctx.put("entity_attrs_forsearch", entity_attrs_forsearch);
        ctx.put("entity_functions", entity.getFunctions());
        ctx.put("entity_key", entity.getPrimarykey()); 
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
