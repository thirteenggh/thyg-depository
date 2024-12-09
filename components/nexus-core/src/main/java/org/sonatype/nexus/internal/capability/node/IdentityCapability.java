package org.sonatype.nexus.internal.capability.node;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.capability.CapabilitySupport;
import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.common.template.TemplateParameters;
import org.sonatype.nexus.ssl.CertificateUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Capability for exposing identity details.
 *
 * @since 3.0
 */
@Named(IdentityCapabilityDescriptor.TYPE_ID)
public class IdentityCapability
    extends CapabilitySupport<IdentityCapabilityConfiguration>
{
  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("%s")
    String description(String nodeId);
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final NodeAccess nodeAccess;

  @Inject
  public IdentityCapability(final NodeAccess nodeAccess) {
    this.nodeAccess = checkNotNull(nodeAccess);
  }

  @Override
  protected IdentityCapabilityConfiguration createConfig(final Map<String, String> properties) {
    return new IdentityCapabilityConfiguration(properties);
  }

  // FIXME: This does not actually work, will have to add some sort of hook/condition/magic
  //@Override
  //protected void onRemove(final IdentityCapabilityConfiguration config) throws Exception {
  //  // HACK: until we have a condition to prevent this
  //  throw new IllegalStateException("Capability can not be removed");
  //}

  @Override
  protected String renderDescription() throws Exception {
    return messages.description(nodeAccess.getId());
  }

  @Override
  protected String renderStatus() throws Exception {
    return render(IdentityCapabilityDescriptor.TYPE_ID + "-status.vm", new TemplateParameters()
        .set("nodeId", nodeAccess.getId())
        .set("fingerprint", nodeAccess.getFingerprint())
        .set("pem", CertificateUtil.serializeCertificateInPEM(nodeAccess.getCertificate()))
        .set("detail", nodeAccess.getCertificate().toString())
    );
  }
}
