
package com.util.auto.model;

import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name="project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {

    @XmlElement(name="name")
    protected String name;
    @XmlElement(name="projectpath")
    protected String projectpath;
    @XmlElement(name="packagename")
    protected String packagename;
    @XmlElement(name="packagepath")
    protected String packagepath;
    @XmlElement(name="packagetype")
    protected String packagetype;
    @XmlElement(name="description")
    protected String description;
    @XmlElement(name="dbdriver")
    protected String dbdriver;
    @XmlElement(name="dburl")
    protected String dburl;
    @XmlElement(name="dbusername")
    protected String dbusername;
    @XmlElement(name="dbpassword")
    protected String dbpassword;
    @XmlElement(name="tables")
    protected String tables;
    @XmlElement(name="tablefilter")
    protected String tablefilter;
    protected List<String> tablelist;
    @XmlElement(name="ignoretables")
    protected String ignoretables;
    protected List<String> ignoretablelist;
    @XmlElementWrapper(name="entities")
    @XmlElement(name="entity")
    protected List<Entity> entities;
    protected List<Entity> apiEntities;

    public String getProjectpath() {
        return projectpath;
    }

    public void setProjectpath(String projectpath) {
        this.projectpath = projectpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getPackagepath() {
    	if(packagepath!=null) {
    		return packagepath;
    	}
    	String temppath = "";
		String pname = this.getPackagename();
		String[] pStrings = pname.split("\\.");
		for (String p : pStrings) {
			temppath = temppath+"/"+p;
		}
		packagepath = temppath;
        return packagepath;
    }

    public void setPackagepath(String packagepath) {
        this.packagepath = packagepath;
    }

    public String getPackagetype() {
        return packagetype;
    }

    public void setPackagetype(String packagetype) {
        this.packagetype = packagetype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

	public String getDbdriver() {
		return dbdriver;
	}

	public void setDbdriver(String dbdriver) {
		this.dbdriver = dbdriver;
	}

	public String getDburl() {
		return dburl;
	}

	public void setDburl(String dburl) {
		this.dburl = dburl;
	}

	public String getDbusername() {
		return dbusername;
	}

	public void setDbusername(String dbusername) {
		this.dbusername = dbusername;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public String getTables() {
		return tables;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}

	public List<String> getTablelist() {
		if(this.tablelist!=null) {
			return this.tablelist;
		}
		if(this.tables!=null && this.tables.trim().length()>0) {
			this.tablelist =Arrays.asList(this.tables.split(","));
		}
		return this.tablelist;
	}


	public String getIgnoretables() {
		return ignoretables;
	}

	public void setIgnoretables(String ignoretables) {
		this.ignoretables = ignoretables;
	}

	public List<String> getIgnoretablelist() {
		if(this.ignoretablelist!=null) {
			return this.ignoretablelist;
		}

		if(this.ignoretables!=null && this.ignoretables.trim().length()>0) {
			this.ignoretablelist =Arrays.asList(this.ignoretables.split(","));
		}
		return this.ignoretablelist;
	}

	public String getTablefilter() {
		return tablefilter;
	}

	public void setTablefilter(String tablefilter) {
		this.tablefilter = tablefilter;
	}

    public List<Entity> getApiEntities() {
        if(apiEntities != null){
            return apiEntities;
        }
        this.buildEntity();
        return apiEntities;
    }

    public void setApiEntities(List<Entity> apiEntities) {
        this.apiEntities = apiEntities;
    }

    public boolean buildEntity(){
        this.apiEntities = new ArrayList<>();
        for (Entity entity : this.getEntities()) {
            entity.buildFields();
            if(entity.isOutapi()){
                this.apiEntities.add(entity);
            }
        }
        return  false;
    }
}
