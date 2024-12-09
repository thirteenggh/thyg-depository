package org.sonatype.nexus.security.privilege;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.formfields.FormField;
import org.sonatype.nexus.formfields.StringTextFormField;
import org.sonatype.nexus.security.config.CPrivilege;
import org.sonatype.nexus.security.config.CPrivilegeBuilder;
import org.sonatype.nexus.security.privilege.rest.ApiPrivilegeApplication;
import org.sonatype.nexus.security.privilege.rest.ApiPrivilegeApplicationRequest;
import org.sonatype.nexus.security.privilege.rest.PrivilegeAction;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.apache.shiro.authz.Permission;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Application {@link PrivilegeDescriptor}.
 *
 * @see ApplicationPermission
 * @since 3.0
 */
@Named(ApplicationPrivilegeDescriptor.TYPE)
@Singleton
public class ApplicationPrivilegeDescriptor
    extends PrivilegeDescriptorSupport<ApiPrivilegeApplication, ApiPrivilegeApplicationRequest>
{
  public static final String TYPE = "application";

  public static final String P_DOMAIN = "domain";

  public static final String P_ACTIONS = "actions";

  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("Application")
    String name();

    @DefaultMessage("Domain")
    String domain();

    @DefaultMessage("The domain for the privilege")
    String domainHelp();

    @DefaultMessage("Actions")
    String actions();

    @DefaultMessage("The comma-delimited list of actions")
    String actionsHelp();
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final List<FormField> formFields;

  public ApplicationPrivilegeDescriptor() {
    super(TYPE);
    this.formFields = ImmutableList.of(
        new StringTextFormField(
            P_DOMAIN,
            messages.domain(),
            messages.domainHelp(),
            FormField.MANDATORY
        ),
        new StringTextFormField(
            P_ACTIONS,
            messages.actions(),
            messages.actionsHelp(),
            FormField.MANDATORY
        )
    );
  }

  @Override
  public Permission createPermission(final CPrivilege privilege) {
    checkNotNull(privilege);
    String domain = readProperty(privilege, P_DOMAIN, ALL);
    List<String> actions = readListProperty(privilege, P_ACTIONS, ALL);
    return new ApplicationPermission(domain, actions);
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

  public static String id(final String domain, final String... actions) {
    return String.format("%s-%s", domain, Joiner.on(',').join(actions));
  }

  public static CPrivilege privilege(final String domain, final String... actions) {
    return new CPrivilegeBuilder()
        .type(TYPE)
        .id(id(domain, actions))
        .property(P_DOMAIN, domain)
        .property(P_ACTIONS, actions)
        .create();
  }

  @Override
  public ApiPrivilegeApplication createApiPrivilegeImpl(final Privilege privilege) {
    return new ApiPrivilegeApplication(privilege);
  }

  @Override
  public void validate(final ApiPrivilegeApplicationRequest apiPrivilege) {
    validateActions(apiPrivilege, PrivilegeAction.getCrudActions());
  }
}
