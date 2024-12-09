package org.sonatype.nexus.repository.apt.internal;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Type;
import org.sonatype.nexus.repository.RecipeSupport;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.view.handlers.HighAvailabilitySupportChecker;

/**
 * Support for Apt recipes
 *
 * @since 3.17
 */
public abstract class AptRecipeSupport
    extends RecipeSupport
{
  private HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  static {
    Configuration.addSensitiveFieldName("aptSigning");
  }

  protected AptRecipeSupport(final HighAvailabilitySupportChecker highAvailabilitySupportChecker,
                             final Type type,
                             final Format format)
  {
    super(type, format);
    this.highAvailabilitySupportChecker = highAvailabilitySupportChecker;
  }

  @Override
  public boolean isFeatureEnabled() {
    final boolean formatSupportHighAvailability =
        highAvailabilitySupportChecker.isSupported(getFormat().getValue());
    return formatSupportHighAvailability;
  }
}
