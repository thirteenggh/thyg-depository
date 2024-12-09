package org.sonatype.nexus.plugins.defaultrole.internal;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.capability.CapabilityDescriptorSupport;
import org.sonatype.nexus.capability.CapabilityType;
import org.sonatype.nexus.formfields.ComboboxFormField;
import org.sonatype.nexus.formfields.FormField;

import static java.util.Collections.singletonList;
import static org.sonatype.nexus.capability.CapabilityType.capabilityType;
import static org.sonatype.nexus.plugins.defaultrole.internal.DefaultRoleCapabilityConfiguration.P_ROLE;

/**
 * Descriptor of the {@link DefaultRoleCapability} for UI configuration
 *
 * @since 3.22
 */
@Named(DefaultRoleCapabilityDescriptor.TYPE_ID)
@Singleton
public class DefaultRoleCapabilityDescriptor
    extends CapabilityDescriptorSupport<DefaultRoleCapabilityConfiguration>
{
  public static final String TYPE_ID = "defaultrole";

  public static final CapabilityType TYPE = capabilityType(TYPE_ID);

  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("Default Role")
    String name();

    @DefaultMessage("Role")
    String roleLabel();

    @DefaultMessage("The role which is automatically granted to authenticated users")
    String roleHelp();
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final ComboboxFormField role;

  public DefaultRoleCapabilityDescriptor() {
    setExposed(true);
    setHidden(false);

    this.role = new ComboboxFormField<String>(
        P_ROLE,
        messages.roleLabel(),
        messages.roleHelp(),
        FormField.MANDATORY
    ).withStoreApi("coreui_Role.read");
  }

  @Override
  public CapabilityType type() {
    return TYPE;
  }

  @Override
  public String name() {
    return messages.name();
  }

  @Override
  public List<FormField> formFields() {
    return singletonList(role);
  }

  @Override
  protected String renderAbout() {
    return render(TYPE_ID + "-about.vm");
  }
}
