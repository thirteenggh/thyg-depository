package org.sonatype.nexus.coreui.internal.wonderland;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Status exchange object.
 *
 * @since 3.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "status", propOrder = {
    "edition",
    "version"
})
@XmlRootElement(name = "status")
public class StatusXO
{
  @XmlElement(required = true)
  @JsonProperty("edition")
  private String edition;

  @XmlElement(required = true)
  @JsonProperty("version")
  private String version;

  public String getEdition() {
    return edition;
  }

  public void setEdition(final String edition) {
    this.edition = edition;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }
}
