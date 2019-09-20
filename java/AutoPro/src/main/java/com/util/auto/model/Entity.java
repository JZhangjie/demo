
package com.util.auto.model;

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
    protected List<Field> fields_forsearch;
    protected List<Field> formFields;
    protected List<Field> conditionFields;
    protected List<Field> tableFields;
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
    @XmlElement(name = "outapi")
    protected boolean outapi=true;

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

    public List<Field> getFields_forsearch() {
        if(this.fields_forsearch != null){
            return this.fields_forsearch;
        }
        this.buildFields();
        return fields_forsearch;
    }

    public void setFields_forsearch(List<Field> fields_forsearch) {
        this.fields_forsearch = fields_forsearch;
    }

    public List<Field> getFormFields() {
        if(this.formFields != null){
            return this.formFields;
        }
        this.buildFields();
        return formFields;
    }

    public void setFormFields(List<Field> formFields) {
        this.formFields = formFields;
    }

    public List<Field> getConditionFields() {
        if(this.conditionFields != null){
            return this.conditionFields;
        }
        this.buildFields();
        return conditionFields;
    }

    public void setConditionFields(List<Field> conditionFields) {
        this.conditionFields = conditionFields;
    }

    public List<Field> getTableFields() {
        if(this.tableFields != null){
            return this.tableFields;
        }
        this.buildFields();
        return tableFields;
    }

    public void setTableFields(List<Field> tableFields) {
        this.tableFields = tableFields;
    }

    public String getName() {
        if(name != null){
            return name;
        }
        name = this.getTable().substring(0,1).toUpperCase()+this.getTable().substring(1);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamelow() {
        if(namelow != null){
            return  namelow;
        }
        namelow = this.getName().toLowerCase();
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

    public boolean isOutapi() {
        return outapi;
    }

    public void setOutapi(boolean outapi) {
        this.outapi = outapi;
    }

    @Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Entity oField = (Entity)obj;
		
		return oField==null?false:oField.getTable().toLowerCase().equals(this.getTable().toLowerCase());
	}

	public boolean buildFields(){
        this.fields_forsearch= new ArrayList<Field>();
        this.tableFields= new ArrayList<Field>();
        this.formFields= new ArrayList<Field>();
        this.conditionFields= new ArrayList<Field>();
        if(this.getFields()!=null) {
            for (Field field : this.getFields()) {
                String operate = field.getOperate();
                if(operate!=null && operate.contains("table")) {
                    tableFields.add(field);
                }
                if(operate!=null && operate.contains("form")) {
                    formFields.add(field);
                }
                if(operate!=null && operate.contains("condition")) {
                    conditionFields.add(field);
                }
                if(field.isSearch()){
                    fields_forsearch.add(field);
                }
            }
            return true;
        }
        return  false;
    }
}
