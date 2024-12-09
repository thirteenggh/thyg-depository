package org.sonatype.nexus.security.privilege.rest;

import java.util.Collection;

import org.sonatype.nexus.security.internal.rest.NexusSecurityApiConstants;
import org.sonatype.nexus.security.privilege.ApplicationPrivilegeDescriptor;
import org.sonatype.nexus.security.privilege.Privilege;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @since 3.19
 */
public class ApiPrivilegeApplication
    extends ApiPrivilegeWithActions
{
  public static final String DOMAIN_KEY = "domain";

  @NotBlank
  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_DOMAIN_DESCRIPTION)
  private String domain;

  /**
   * for deserialization
   */
  private ApiPrivilegeApplication() {
    super(ApplicationPrivilegeDescriptor.TYPE);
  }

  public ApiPrivilegeApplication(final String name,
                                 final String description,
                                 final boolean readOnly,
                                 final String domain,
                                 final Collection<PrivilegeAction> actions)
  {
    super(ApplicationPrivilegeDescriptor.TYPE, name, description, readOnly, actions);
    this.domain = domain;
  }

  public ApiPrivilegeApplication(final Privilege privilege) {
    super(privilege);
    domain = privilege.getPrivilegeProperty(DOMAIN_KEY);
  }

  public void setDomain(final String domain) {
    this.domain = domain;
  }

  public String getDomain() {
    return domain;
  }

  @Override
  protected Privilege doAsPrivilege(final Privilege privilege) {
    super.doAsPrivilege(privilege);
    privilege.addProperty(DOMAIN_KEY, domain);
    return privilege;
  }

  @Override
  protected String doAsActionString() {
    return toCrudActionString();
  }
}
