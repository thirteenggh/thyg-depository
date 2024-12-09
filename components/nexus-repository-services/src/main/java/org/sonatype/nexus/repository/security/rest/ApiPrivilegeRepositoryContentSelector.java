package org.sonatype.nexus.repository.security.rest;

import java.util.Collection;

import org.sonatype.nexus.repository.security.RepositoryContentSelectorPrivilegeDescriptor;
import org.sonatype.nexus.security.internal.rest.NexusSecurityApiConstants;
import org.sonatype.nexus.security.privilege.Privilege;
import org.sonatype.nexus.security.privilege.rest.PrivilegeAction;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @since 3.19
 */
public class ApiPrivilegeRepositoryContentSelector
    extends ApiPrivilegeWithRepository
{
  public static final String CSEL_KEY = "contentSelector";

  @NotBlank
  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_CONTENT_SELECTOR_DESCRIPTION)
  private String contentSelector;

  /**
   * for deserialization
   */
  private ApiPrivilegeRepositoryContentSelector() {
    super(RepositoryContentSelectorPrivilegeDescriptor.TYPE);
  }

  public ApiPrivilegeRepositoryContentSelector(final String name,
                                               final String description,
                                               final boolean readOnly,
                                               final String format,
                                               final String repository,
                                               final String contentSelector,
                                               final Collection<PrivilegeAction> actions)
  {
    super(RepositoryContentSelectorPrivilegeDescriptor.TYPE, name, description, readOnly, format, repository, actions);
    this.contentSelector = contentSelector;
  }

  public ApiPrivilegeRepositoryContentSelector(final Privilege privilege) {
    super(privilege);
    contentSelector = privilege.getPrivilegeProperty(CSEL_KEY);
  }

  public void setContentSelector(final String contentSelector) {
    this.contentSelector = contentSelector;
  }

  public String getContentSelector() {
    return contentSelector;
  }

  @Override
  protected Privilege doAsPrivilege(final Privilege privilege) {
    super.doAsPrivilege(privilege);
    privilege.addProperty(CSEL_KEY, contentSelector);
    return privilege;
  }
}
