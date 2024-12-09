package org.sonatype.nexus.rapture.internal.branding;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.capability.CapabilityDescriptorSupport;
import org.sonatype.nexus.capability.CapabilityType;
import org.sonatype.nexus.capability.Tag;
import org.sonatype.nexus.capability.Taggable;
import org.sonatype.nexus.formfields.CheckboxFormField;
import org.sonatype.nexus.formfields.FormField;
import org.sonatype.nexus.formfields.TextAreaFormField;

import com.google.common.collect.Lists;

/**
 * {@link BrandingCapability} descriptor.
 *
 * @since 3.0
 */
@Named(BrandingCapabilityDescriptor.TYPE_ID)
@Singleton
public class BrandingCapabilityDescriptor
    extends CapabilityDescriptorSupport<BrandingCapabilityConfiguration>
    implements Taggable
{
  public static final String TYPE_ID = "rapture.branding";

  public static final CapabilityType TYPE = CapabilityType.capabilityType(TYPE_ID);

  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("UI: Branding")
    String name();

    @DefaultMessage("Enable header")
    String headerEnabledLabel();

    @DefaultMessage("Enable branding header HTML snippet.")
    String headerEnabledHelp();

    @DefaultMessage("Header HTML snippet")
    String headerHtmlLabel();

    @DefaultMessage("An HTML snippet to be included in branding header. Use '$baseUrl' to insert the base URL of the " +
        "server (e.g. to reference an image)")
    String headerHtmlHelp();

    @DefaultMessage("Enable footer")
    String footerEnabledLabel();

    @DefaultMessage("Enable branding footer HTML snippet.")
    String footerEnabledHelp();

    @DefaultMessage("Footer HTML snippet")
    String footerHtmlLabel();

    @DefaultMessage("An HTML snippet to be included in branding footer. Use '$baseUrl' to insert the base URL of the " +
        "server (e.g. to reference an image)")
    String footerHtmlHelp();
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final List<FormField> formFields;

  public BrandingCapabilityDescriptor() {
    formFields = Lists.<FormField>newArrayList(
        new CheckboxFormField(
            BrandingCapabilityConfiguration.HEADER_ENABLED,
            messages.headerEnabledLabel(),
            messages.headerEnabledHelp(),
            FormField.OPTIONAL
        ).withInitialValue(true),
        new TextAreaFormField(
            BrandingCapabilityConfiguration.HEADER_HTML,
            messages.headerHtmlLabel(),
            messages.headerHtmlHelp(),
            FormField.OPTIONAL
        ),
        new CheckboxFormField(
            BrandingCapabilityConfiguration.FOOTER_ENABLED,
            messages.footerEnabledLabel(),
            messages.footerEnabledHelp(),
            FormField.OPTIONAL
        ).withInitialValue(true),
        new TextAreaFormField(
            BrandingCapabilityConfiguration.FOOTER_HTML,
            messages.footerHtmlLabel(),
            messages.footerHtmlHelp(),
            FormField.OPTIONAL
        )
    );
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
    return formFields;
  }

  @Override
  protected BrandingCapabilityConfiguration createConfig(final Map<String, String> properties) {
    return new BrandingCapabilityConfiguration(properties);
  }

  @Override
  protected String renderAbout() throws Exception {
    return render(TYPE_ID + "-about.vm");
  }

  @Override
  public Set<Tag> getTags() {
    return Tag.tags(Tag.categoryTag("UI"));
  }

}
