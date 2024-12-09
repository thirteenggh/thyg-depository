package org.sonatype.nexus.coreui.internal.wonderland;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auth-token exchange object.
 *
 * @since 3.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authToken", propOrder = {
    "u",
    "p"
})
@XmlRootElement(name = "authToken")
public class AuthTokenXO
{
  @XmlElement(required = true)
  @JsonProperty("u")
  private String u;

  @XmlElement(required = true)
  @JsonProperty("p")
  private String p;

  public String getU() {
    return u;
  }

  public void setU(String value) {
    this.u = value;
  }

  public String getP() {
    return p;
  }

  public void setP(String value) {
    this.p = value;
  }

  public AuthTokenXO withU(String value) {
    setU(value);
    return this;
  }

  public AuthTokenXO withP(String value) {
    setP(value);
    return this;
  }
}
