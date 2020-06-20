//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.20 at 11:44:27 AM CEST 
//


package xwsagent.wroomagent.soap.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rentRequest" type="{http://ftn.com/wroom-agent/xsd}RentRequestSoap"/&gt;
 *         &lt;element name="operation" type="{http://ftn.com/wroom-agent/xsd}operationRents"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rentRequest",
    "operation"
})
@XmlRootElement(name = "SendRentRequest")
public class SendRentRequest {

    @XmlElement(required = true)
    protected RentRequestSoap rentRequest;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected OperationRents operation;

    /**
     * Gets the value of the rentRequest property.
     * 
     * @return
     *     possible object is
     *     {@link RentRequestSoap }
     *     
     */
    public RentRequestSoap getRentRequest() {
        return rentRequest;
    }

    /**
     * Sets the value of the rentRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link RentRequestSoap }
     *     
     */
    public void setRentRequest(RentRequestSoap value) {
        this.rentRequest = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link OperationRents }
     *     
     */
    public OperationRents getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationRents }
     *     
     */
    public void setOperation(OperationRents value) {
        this.operation = value;
    }

}