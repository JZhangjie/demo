package com.util.auto.builder;

import com.util.auto.config.Config;
import com.util.auto.config.ConfigManager;
import com.util.auto.config.VmConfig;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.util.auto.model.Entity;
import com.util.auto.model.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;


public  class FileBuilder {
	private Project project;
	private Config config;

	public FileBuilder(Project project, Config config) {
		this.project = project;
		this.config = config;
	}
	
	public boolean build() {
		if(project != null && project.getEntities() != null && config != null && config.getVmconfigs() != null){
			//构建project相关文件
			System.out.println( "开始》》》："+project.getName());
			project.buildEntity();

			for (VmConfig vmconfig : config.getVmconfigs()) {
				if("project".equals(vmconfig.getType()) ){
					this.buildFiles(project,null,vmconfig);
				}
			}
			//构建entity相关文件
			for (Entity entity : project.getEntities()) {
				System.out.println( "开始生成实体类："+entity.getName() );
				for (VmConfig vmconfig : config.getVmconfigs()) {
					if("entity".equals(vmconfig.getType()) ) {
						System.out.println( "模板文件路径===》："+vmconfig.getVmpath() );
						this.buildFiles(project, entity, vmconfig);
					}
				}
				System.out.println( "完成实体类》》："+entity.getName() );
			}
		}
		return true;
	}
	
	private void buildFiles(Project project, Entity entity,VmConfig vmconfig) {
        Properties pro = new Properties();
        pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        pro.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, vmconfig.getVmpath());
        VelocityEngine velocityEngine = new VelocityEngine(pro);
        velocityEngine.init(); 
        
        VelocityContext ctx = buildContext(project,entity);
		if(vmconfig.isIsdir()){
			for (VmConfig temp : vmconfig.getChildren()) {
				Template actionTpt = velocityEngine.getTemplate(temp.getVmname(),"UTF-8");
				outFile(actionTpt,ctx, ConfigManager.getOutFile(temp,project,entity));
			}
		}else{
			Template actionTpt = velocityEngine.getTemplate(vmconfig.getVmname(),"UTF-8");
			outFile(actionTpt,ctx, ConfigManager.getOutFile(vmconfig,project,entity));
		}
    }
	/*
	 * 使用Entity构建VelocityContext对象
	 */
	private VelocityContext buildContext(Project project,Entity entity) {
        VelocityContext ctx = new VelocityContext(); 
        ctx.put("project", project);
        ctx.put("entity", entity);
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

	private void outFile(Template template, VelocityContext ctx,String filepath) {
		File file = new File(filepath);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filepath);
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
