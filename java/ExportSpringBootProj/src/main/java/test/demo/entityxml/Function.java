
package test.demo.entityxml;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Function {

    @XmlElement(name = "name")
    protected String name;
    @XmlElement(name = "paramtype")
    protected String paramtype;
    @XmlElement(name = "paramname")
    protected String paramname;
    @XmlElement(name = "result")
    protected String result;
    @XmlElement(name = "dbparamtype")
    protected String dbparamtype;
    
    /**
     * ��ȡname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * ����name���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * ��ȡparamtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamtype() {
        return paramtype;
    }

    /**
     * ����paramtype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamtype(String value) {
        this.paramtype = value;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String value) {
        this.paramname = value;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String value) {
        this.result = value;
    }

	public String getDbparamtype() {
		return dbparamtype;
	}

	public void setDbparamtype(String dbparamtype) {
		this.dbparamtype = dbparamtype;
	}

}
