package org.sonatype.nexus.internal.capability.node;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.capability.CapabilityDescriptorSupport;
import org.sonatype.nexus.capability.CapabilityType;
import org.sonatype.nexus.capability.Tag;
import org.sonatype.nexus.capability.Taggable;
import org.sonatype.nexus.formfields.FormField;

import static org.sonatype.nexus.capability.CapabilityType.capabilityType;
import static org.sonatype.nexus.capability.Tag.categoryTag;
import static org.sonatype.nexus.capability.Tag.tags;

/**
 * {@link IdentityCapability} descriptor.
 *
 * @since 3.0
 */
@Named(IdentityCapabilityDescriptor.TYPE_ID)
@Singleton
public class IdentityCapabilityDescriptor
    extends CapabilityDescriptorSupport
    implements Taggable
{
  public static final String TYPE_ID = "node.identity";

  public static final CapabilityType TYPE = capabilityType(TYPE_ID);

  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("Node: Identity")
    String name();
  }

  private static final Messages messages = I18N.create(Messages.class);

  @Override
  public CapabilityType type() {
    return TYPE;
  }

  @Override
  public String name() {
    return messages.name();
  }

  @Override
  public Set<Tag> getTags() {
    return tags(categoryTag("Core"));
  }

  @Override
  public List<FormField> formFields() {
    return Collections.emptyList();
  }

  @Override
  protected String renderAbout() throws Exception {
    return render(TYPE_ID + "-about.vm");
  }
}
