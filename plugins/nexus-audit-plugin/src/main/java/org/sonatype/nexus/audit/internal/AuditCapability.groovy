package org.sonatype.nexus.audit.internal

import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.goodies.i18n.I18N
import org.sonatype.goodies.i18n.MessageBundle
import org.sonatype.goodies.i18n.MessageBundle.DefaultMessage
import org.sonatype.nexus.capability.CapabilityConfigurationSupport
import org.sonatype.nexus.capability.CapabilityDescriptorSupport
import org.sonatype.nexus.capability.CapabilitySupport
import org.sonatype.nexus.capability.CapabilityType
import org.sonatype.nexus.capability.Condition
import org.sonatype.nexus.capability.Tag
import org.sonatype.nexus.capability.Taggable
import org.sonatype.nexus.formfields.FormField

import groovy.transform.PackageScope
import groovy.transform.ToString

import static org.sonatype.nexus.capability.CapabilityType.capabilityType

/**
 * Audit capability.
 *
 * @since 3.1
 */
@Named(AuditCapability.TYPE_ID)
class AuditCapability
    extends CapabilitySupport<Configuration>
{
  public static final String TYPE_ID = 'audit'

  public static final CapabilityType TYPE = capabilityType(TYPE_ID)

  private static interface Messages
      extends MessageBundle
  {
    @DefaultMessage('Audit')
    String name()

    @DefaultMessage('Audit')
    String category()

    @DefaultMessage('Disabled')
    String disabled()

    @DefaultMessage('Enabled')
    String enabled()
  }

  @PackageScope
  static final Messages messages = I18N.create(Messages.class)

  @Inject
  AuditRecorderImpl auditRecorder

  @Override
  protected Configuration createConfig(final Map<String, String> properties) {
    return new Configuration(properties)
  }

  @Override
  @Nullable
  protected String renderDescription() {
    if (context().active) {
      return messages.enabled()
    }
    return messages.disabled()
  }

  @Override
  Condition activationCondition() {
    return conditions().capabilities().passivateCapabilityDuringUpdate()
  }

  @Override
  protected void onActivate(final Configuration config) {
    auditRecorder.enabled = true
  }

  @Override
  protected void onPassivate(final Configuration config) {
    auditRecorder.enabled = false
  }

  //
  // Configuration
  //

  @ToString(includePackage = false, includeNames = true)
  static class Configuration
      extends CapabilityConfigurationSupport
  {
    Configuration(final Map<String, String> properties) {
      // empty
    }
  }

  //
  // Descriptor
  //

  @Named(AuditCapability.TYPE_ID)
  @Singleton
  static public class Descriptor
      extends CapabilityDescriptorSupport<Configuration>
      implements Taggable
  {
    Descriptor() {
      this.exposed = true
      this.hidden = false
    }

    @Override
    CapabilityType type() {
      return TYPE
    }

    @Override
    String name() {
      return messages.name()
    }

    @Override
    List<FormField> formFields() {
      return []
    }

    @Override
    protected Configuration createConfig(final Map<String, String> properties) {
      return new Configuration(properties)
    }

    @Override
    protected String renderAbout() {
      return render("$TYPE_ID-about.vm")
    }

    @Override
    Set<Tag> getTags() {
      return [Tag.categoryTag(messages.category())]
    }
  }
}
