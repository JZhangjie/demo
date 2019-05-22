
package test.demo.entityxml;

import javax.xml.bind.annotation.*;
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
    @XmlElement(name="dbtype")
    protected String dbtype;
    @XmlElementWrapper(name="entities")
    @XmlElement(name="entity")
    protected List<Entity> entities;

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

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
