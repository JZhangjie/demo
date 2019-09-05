package test.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
    	String REGEX = "fo";
        String INPUT = "fooooooooooooooooo";
    	Pattern pattern = Pattern.compile(REGEX);
    	boolean a =  pattern.matcher(INPUT).find();
  
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
		String filter = project.getTablefilter();
		for (String table : tablenams) {
			if(filter!=null && !filter.equals("") && !Pattern.compile(filter).matcher(table).find()) {
				continue;
			}
			if(ntables!=null && ntables.contains(table)) {
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
