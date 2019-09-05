package test.demo;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import test.demo.entityxml.Entity;
import test.demo.entityxml.Field;
import test.demo.entityxml.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.lang.model.element.VariableElement;

public  class ViewBuilder {
	String templateDir = System.getProperty("user.dir")+"\\src\\template\\t2\\view";
	String rootPath = null;
	
	public ViewBuilder(String templateDir,String rootPath) {
		if(templateDir!=null) {
			this.templateDir =templateDir;
		}
		this.rootPath = rootPath;
	}
	
	public boolean build(Project project, Entity entity) {
		if(this.rootPath==null) {
			this.rootPath = project.getProjectpath();
		}
		buildFiles(project,entity);
		return false;
	}
	
	private void buildFiles(Project project, Entity entity) {
        System.out.println( "开始构建页面==》"+entity.getName() );
        Properties pro = new Properties();
        pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        pro.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templateDir);
        VelocityEngine velocityEngine = new VelocityEngine(pro);
        velocityEngine.init(); 
        
        VelocityContext ctx = buildContext(project,entity);
        String operate = entity.getOperate();
        Template actionTpt5 = velocityEngine.getTemplate("data.js.vm","UTF-8"); 
        outFile(actionTpt5,ctx,makeDir(this.rootPath+"/"+entity.getNamelow()+"/"),     "data.js");
        
        if(operate!=null) {
        	if(operate.contains("form")) {
                Template actionTpt = velocityEngine.getTemplate("detail.html.vm","UTF-8"); 
                Template actionTpt2 = velocityEngine.getTemplate("detail.js.vm","UTF-8"); 
                outFile(actionTpt,ctx, makeDir(this.rootPath+"/"+entity.getNamelow()+"/"),     "detail.html");
                outFile(actionTpt2,ctx,makeDir(this.rootPath+"/"+entity.getNamelow()+"/"),     "detail.js");
        	}
        	if(operate.contains("table")) {
            	Template actionTpt3 = velocityEngine.getTemplate("list.html.vm","UTF-8"); 
                Template actionTpt4 = velocityEngine.getTemplate("list.js.vm","UTF-8"); 
                outFile(actionTpt3,ctx, makeDir(this.rootPath+"/"+entity.getNamelow()+"/"),     "list.html");
                outFile(actionTpt4,ctx,makeDir(this.rootPath+"/"+entity.getNamelow()+"/"),     "list.js");
            }

        }
        System.out.println( "页面构建完成==》"+entity.getName() );
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
        ctx.put("project", project);
        ctx.put("entity", entity);
        List<Field> formFields= new ArrayList<>();
        List<Field> tableFields= new ArrayList<>();
        if(entity.getFields()!=null) {
        	for (Field field : entity.getFields()) {
        		String operate = field.getOperate();
        		if(operate!=null && operate.contains("table")) {
        			tableFields.add(field);
                }
        		if(operate!=null && operate.contains("form")) {
        			formFields.add(field);
                }
			}
        }
        ctx.put("formFields", formFields);
        ctx.put("tableFields", tableFields);
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
