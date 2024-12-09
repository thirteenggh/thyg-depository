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
public class ApiPrivilegeApplicationRequest
    extends ApiPrivilegeWithActionsRequest
{
  public static final String DOMAIN_KEY = "domain";

  @NotBlank
  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_DOMAIN_DESCRIPTION)
  private String domain;

  /**
   * for deserialization
   */
  private ApiPrivilegeApplicationRequest() {
    super();
  }

  public ApiPrivilegeApplicationRequest(final String name,
                                        final String description,
                                        final String domain,
                                        final Collection<PrivilegeAction> actions)
  {
    super(name, description, actions);
    this.domain = domain;
  }

  public ApiPrivilegeApplicationRequest(final Privilege privilege) {
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
    privilege.setType(ApplicationPrivilegeDescriptor.TYPE);
    privilege.addProperty(DOMAIN_KEY, domain);
    return privilege;
  }

  @Override
  protected String doAsActionString() {
    return toCrudActionString();
  }
}
