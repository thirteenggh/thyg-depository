package org.sonatype.nexus.repository.httpbridge.legacy;

import java.util.Map;

import javax.inject.Named;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.capability.CapabilitySupport;

/**
 * Legacy URL capability.
 *
 * @since 3.7
 */
@Named(LegacyUrlCapabilityDescriptor.TYPE_ID)
public class LegacyUrlCapability
    extends CapabilitySupport<LegacyUrlCapabilityConfiguration>
{
  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("NXRM2 style urls enabled")
    String description();
  }

  private static final Messages messages = I18N.create(Messages.class);

  @Override
  protected LegacyUrlCapabilityConfiguration createConfig(final Map<String, String> properties) {
    return new LegacyUrlCapabilityConfiguration();
  }

  @Override
  protected String renderDescription() throws Exception {
    if (context().isActive()) {
      return messages.description();
    }
    return null;
  }
}
