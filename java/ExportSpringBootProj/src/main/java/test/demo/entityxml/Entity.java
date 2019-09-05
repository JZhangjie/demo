
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
    @XmlElement(name="primarykey")
    protected Field primarykey;
    @XmlElement(name = "name")
    protected String name;
    @XmlElement(name = "namelow")
    protected String namelow;
    @XmlElement(name = "table")
    protected String table;
    @XmlElement(name = "operate")
    protected String operate;

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

	public Field getPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(Field primarykey) {
		this.primarykey = primarykey;
	}
	
	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Entity oField = (Entity)obj;
		
		return oField==null?false:oField.getTable().toLowerCase().equals(this.getTable().toLowerCase());
	}
}
