package org.sonatype.nexus.script.plugin.internal.rest;

import java.util.Collection;

import org.sonatype.nexus.script.plugin.internal.security.ScriptPrivilegeDescriptor;
import org.sonatype.nexus.security.internal.rest.NexusSecurityApiConstants;
import org.sonatype.nexus.security.privilege.Privilege;
import org.sonatype.nexus.security.privilege.rest.ApiPrivilegeWithActions;
import org.sonatype.nexus.security.privilege.rest.PrivilegeAction;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @since 3.19
 */
public class ApiPrivilegeScript
    extends ApiPrivilegeWithActions
{
  public static final String SCRIPT_KEY = "name";

  @NotBlank
  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_SCRIPT_DESCRIPTION)
  private String scriptName;

  /**
   * for deserialization
   */
  private ApiPrivilegeScript() {
    super(ScriptPrivilegeDescriptor.TYPE);
  }

  public ApiPrivilegeScript(final String name,
                            final String description,
                            final boolean readOnly,
                            final String scriptName,
                            final Collection<PrivilegeAction> actions)
  {
    super(ScriptPrivilegeDescriptor.TYPE, name, description, readOnly, actions);
    this.scriptName = scriptName;
  }

  public ApiPrivilegeScript(final Privilege privilege) {
    super(privilege);
    scriptName = privilege.getPrivilegeProperty(SCRIPT_KEY);
  }

  public void setScriptName(final String scriptName) {
    this.scriptName = scriptName;
  }

  public String getScriptName() {
    return scriptName;
  }

  @Override
  protected Privilege doAsPrivilege(final Privilege privilege) {
    super.doAsPrivilege(privilege);
    privilege.addProperty(SCRIPT_KEY, scriptName);
    return privilege;
  }

  @Override
  protected String doAsActionString() {
    return toBreadRunActionString();
  }
}
