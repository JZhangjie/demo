
package test.demo.entityxml;

import java.util.List;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
public class Field {

    @XmlElement(name = "name")
    protected String name;
    @XmlElement(name = "nameupper")
    protected String nameupper;
    @XmlElement(name = "dbtype")
    protected String dbtype;
    @XmlElement(name = "javatype")
    protected String javatype;
    @XmlElement(name = "auto")
    protected boolean auto;
    @XmlElement(name = "search")
    protected boolean search;
    @XmlElement(name = "operate")
    protected String operate;    		//操作类型  table:列表显示  form:表单显示 condition:高级查询
    @XmlElement(name = "operatetype")
    protected String operatetype;    	//string,text,select,date,time,number
    @XmlElement(name = "label")
    protected String label;    

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }


    public String getNameupper() {
        return nameupper;
    }


    public void setNameupper(String value) {
        this.nameupper = value;
    }


    public String getDbtype() {
        return dbtype;
    }


    public void setDbtype(String value) {
        this.dbtype = value;
    }

    public String getJavatype() {
        return javatype;
    }


    public void setJavatype(String value) {
        this.javatype = value;
    }

	public boolean getAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}


	public boolean isSearch() {
		return search;
	}


	public void setSearch(boolean search) {
		this.search = search;
	}


	public String getOperate() {
		return operate;
	}


	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	public String getOperatetype() {
		return operatetype;
	}


	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Field oField = (Field)obj;
		
		return oField==null?false:oField.getName().equals(this.getName());
	}
    
}
