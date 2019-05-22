
package test.demo.entityxml;

import javax.xml.bind.annotation.*;


/**
 * <p>field complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="field">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="nameupper" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dbtype" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="javatype" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
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
     * ��ȡnameupper���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameupper() {
        return nameupper;
    }

    /**
     * ����nameupper���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameupper(String value) {
        this.nameupper = value;
    }

    /**
     * ��ȡdbtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbtype() {
        return dbtype;
    }

    /**
     * ����dbtype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbtype(String value) {
        this.dbtype = value;
    }

    /**
     * ��ȡjavatype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavatype() {
        return javatype;
    }

    /**
     * ����javatype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavatype(String value) {
        this.javatype = value;
    }

}
