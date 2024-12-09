package org.sonatype.nexus.repository.security.rest;

import java.util.Collection;

import org.sonatype.nexus.security.internal.rest.NexusSecurityApiConstants;
import org.sonatype.nexus.security.privilege.Privilege;
import org.sonatype.nexus.security.privilege.rest.ApiPrivilegeWithActionsRequest;
import org.sonatype.nexus.security.privilege.rest.PrivilegeAction;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @since 3.19
 */
public abstract class ApiPrivilegeWithRepositoryRequest
    extends ApiPrivilegeWithActionsRequest
{
  public static final String FORMAT_KEY = "format";

  public static final String REPOSITORY_KEY = "repository";

  @NotBlank
  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_REPOSITORY_FORMAT_DESCRIPTION)
  private String format;

  @NotBlank
  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_REPOSITORY_DESCRIPTION)
  private String repository;

  public ApiPrivilegeWithRepositoryRequest() {
    super();
  }

  public ApiPrivilegeWithRepositoryRequest(final String name,
                                           final String description,
                                           final String format,
                                           final String repository,
                                           final Collection<PrivilegeAction> actions)
  {
    super(name, description, actions);
    this.format = format;
    this.repository = repository;
  }

  public ApiPrivilegeWithRepositoryRequest(final Privilege privilege) {
    super(privilege);
    format = privilege.getPrivilegeProperty(FORMAT_KEY);
    repository = privilege.getPrivilegeProperty(REPOSITORY_KEY);
  }

  public void setRepository(final String repository) {
    this.repository = repository;
  }

  public void setFormat(final String format) {
    this.format = format;
  }

  public String getRepository() {
    return repository;
  }

  public String getFormat() {
    return format;
  }

  @Override
  protected Privilege doAsPrivilege(final Privilege privilege) {
    super.doAsPrivilege(privilege);
    privilege.addProperty(FORMAT_KEY, getFormat());
    privilege.addProperty(REPOSITORY_KEY, getRepository());

    return privilege;
  }

  @Override
  protected String doAsActionString() {
    return toBreadActionString();
  }
}
