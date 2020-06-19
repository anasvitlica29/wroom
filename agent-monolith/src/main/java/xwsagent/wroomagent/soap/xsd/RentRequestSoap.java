//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.19 at 01:10:17 PM CEST 
//


package xwsagent.wroomagent.soap.xsd;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RentRequestSoap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RentRequestSoap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://ftn.com/wroom-agent/xsd}id"/&gt;
 *         &lt;element name="status" type="{http://ftn.com/wroom-agent/xsd}status"/&gt;
 *         &lt;element name="fromDate" type="{http://ftn.com/wroom-agent/xsd}date"/&gt;
 *         &lt;element name="toDate" type="{http://ftn.com/wroom-agent/xsd}date"/&gt;
 *         &lt;element name="requestedUserUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ad" type="{http://ftn.com/wroom-agent/xsd}id"/&gt;
 *         &lt;element name="rentReport" type="{http://ftn.com/wroom-agent/xsd}id"/&gt;
 *         &lt;element name="bundle" type="{http://ftn.com/wroom-agent/xsd}id"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RentRequestSoap", propOrder = {
    "id",
    "status",
    "fromDate",
    "toDate",
    "requestedUserUsername",
    "ad",
    "rentReport",
    "bundle"
})
public class RentRequestSoap {

	@XmlElement(required = true)
    protected long id;
    
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Status status;
    
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected Date fromDate;
    
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected Date toDate;
    
    @XmlElement(required = true)
    protected String requestedUserUsername;
    
    protected long ad;
    
    protected long rentReport;
    
    protected long bundle;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the fromDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * Sets the value of the fromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFromDate(Date value) {
        this.fromDate = value;
    }

    /**
     * Gets the value of the toDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * Sets the value of the toDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setToDate(Date value) {
        this.toDate = value;
    }

    /**
     * Gets the value of the requestedUserUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedUserUsername() {
        return requestedUserUsername;
    }

    /**
     * Sets the value of the requestedUserUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedUserUsername(String value) {
        this.requestedUserUsername = value;
    }

    /**
     * Gets the value of the ad property.
     * 
     */
    public long getAd() {
        return ad;
    }

    /**
     * Sets the value of the ad property.
     * 
     */
    public void setAd(long value) {
        this.ad = value;
    }

    /**
     * Gets the value of the rentReport property.
     * 
     */
    public long getRentReport() {
        return rentReport;
    }

    /**
     * Sets the value of the rentReport property.
     * 
     */
    public void setRentReport(long value) {
        this.rentReport = value;
    }

    /**
     * Gets the value of the bundle property.
     * 
     */
    public long getBundle() {
        return bundle;
    }

    /**
     * Sets the value of the bundle property.
     * 
     */
    public void setBundle(long value) {
        this.bundle = value;
    }

}
