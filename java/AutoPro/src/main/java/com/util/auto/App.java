package com.util.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.util.auto.config.Config;
import com.util.auto.util.*;
import com.util.auto.builder.*;
import com.util.auto.config.ConfigManager;
import com.util.auto.model.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

		workOnXmlAndDB();

    }
    
    public static void workOnlyXml() {
		Config config = ConfigManager.getConfigs();
		Project project = ProjectXMLReader.getProjectFromXml(config.getXmlpath());
		FileBuilder builder = new FileBuilder(project,config);
		builder.build();
    }
    
    public static void workOnXmlAndDB() {
		Config config = ConfigManager.getConfigs();
		Project project = ProjectXMLReader.getProjectFromXml(config.getXmlpath());
		FileBuilder builder = new FileBuilder(project,config);

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
		builder.build();
    }
}
