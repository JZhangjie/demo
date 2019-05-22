
package test.demo.entityxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Entity {

    @XmlElementWrapper(name="functions")
    @XmlElement(name="function")
    protected List<Function> functions;
    @XmlElementWrapper(name="fields")
    @XmlElement(name="field")
    protected List<Field> fields;
    @XmlElement(name = "name")
    protected String name;
    @XmlElement(name = "namelow")
    protected String namelow;
    @XmlElement(name = "table")
    protected String table;

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamelow() {
        return namelow;
    }

    public void setNamelow(String namelow) {
        this.namelow = namelow;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
