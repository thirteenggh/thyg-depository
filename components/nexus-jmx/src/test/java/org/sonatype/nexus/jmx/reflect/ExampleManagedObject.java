package org.sonatype.nexus.jmx.reflect;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.jmx.ObjectNameEntry;

/**
 * ???
 */
@Named
@Singleton
@ManagedObject(
    domain = "org.sonatype.nexus.jmx",
    entries = {
        @ObjectNameEntry(name="foo", value="bar")
    },
    description = "Example managed object"
)
public class ExampleManagedObject
{
  private String name;

  private String password;

  // R/W attribute

  @ManagedAttribute
  public String getName() {
    return name;
  }

  @ManagedAttribute
  public void setName(final String name) {
    this.name = name;
  }

  // W-only attribute

  public String getPassword() {
    return password;
  }

  @ManagedAttribute(
      description = "Set password"
  )
  public void setPassword(final String password) {
    this.password = password;
  }

  // Operation

  @ManagedOperation(
      description = "Reset name"
  )
  public void resetName() {
    this.name = null;
  }
}
