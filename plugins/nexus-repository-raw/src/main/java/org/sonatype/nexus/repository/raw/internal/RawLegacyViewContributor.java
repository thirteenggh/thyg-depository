package org.sonatype.nexus.repository.raw.internal;

import java.util.regex.Pattern;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.httpbridge.LegacyViewConfiguration;
import org.sonatype.nexus.repository.httpbridge.LegacyViewContributor;

/**
 * Legacy view contributor for Raw.
 *
 * @since 3.7
 */
@Named
@Singleton
public class RawLegacyViewContributor
    implements LegacyViewContributor
{
  @Override
  public LegacyViewConfiguration contribute() {
    return new LegacyViewConfiguration()
    {
      @Override
      public String getFormat() {
        return RawFormat.NAME;
      }

      @Override
      public Pattern getRequestPattern() {
        return Pattern.compile("/content/sites/.*");
      }
    };
  }
}
