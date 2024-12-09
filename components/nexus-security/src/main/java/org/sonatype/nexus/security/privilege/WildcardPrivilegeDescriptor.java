package org.sonatype.nexus.security.privilege;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.formfields.FormField;
import org.sonatype.nexus.formfields.StringTextFormField;
import org.sonatype.nexus.security.authz.WildcardPermission2;
import org.sonatype.nexus.security.config.CPrivilege;
import org.sonatype.nexus.security.config.CPrivilegeBuilder;
import org.sonatype.nexus.security.privilege.rest.ApiPrivilegeWildcard;
import org.sonatype.nexus.security.privilege.rest.ApiPrivilegeWildcardRequest;

import com.google.common.collect.ImmutableList;
import org.apache.shiro.authz.Permission;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wildcard {@link PrivilegeDescriptor}.
 *
 * @see WildcardPermission2
 * @since 3.0
 */
@Named(WildcardPrivilegeDescriptor.TYPE)
@Singleton
public class WildcardPrivilegeDescriptor
    extends PrivilegeDescriptorSupport<ApiPrivilegeWildcard, ApiPrivilegeWildcardRequest>
{
  public static final String TYPE = "wildcard";

  public static final String P_PATTERN = "pattern";

  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("Wildcard")
    String name();

    @DefaultMessage("Pattern")
    String pattern();

    @DefaultMessage("The regex pattern")
    String patternHelp();
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final List<FormField> formFields;

  public WildcardPrivilegeDescriptor() {
    super(TYPE);
    this.formFields = ImmutableList.of(
        new StringTextFormField(
            P_PATTERN,
            messages.pattern(),
            messages.patternHelp(),
            FormField.MANDATORY
        )
    );
  }

  @Override
  public Permission createPermission(final CPrivilege privilege) {
    checkNotNull(privilege);
    String pattern = readProperty(privilege, P_PATTERN);
    return new WildcardPermission2(pattern);
  }

  @Override
  public List<FormField> getFormFields() {
    return formFields;
  }

  @Override
  public String getName() {
    return messages.name();
  }

  //
  // Helpers
  //

  public static String id(final String pattern) {
    return pattern;
  }

  public static CPrivilege privilege(final String pattern) {
    return new CPrivilegeBuilder()
        .type(TYPE)
        .id(id(pattern))
        .property(P_PATTERN, pattern)
        .create();
  }

  @Override
  public ApiPrivilegeWildcard createApiPrivilegeImpl(final Privilege privilege) {
    return new ApiPrivilegeWildcard(privilege);
  }

  @Override
  public void validate(final ApiPrivilegeWildcardRequest apiPrivilege) {
    //not validating anything in particular here
  }
}
