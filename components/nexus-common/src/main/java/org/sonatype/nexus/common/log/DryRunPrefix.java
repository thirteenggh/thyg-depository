package org.sonatype.nexus.common.log;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Prefix for use when logging actions while in dry-run mode (i.e. log actions, but make no changes).
 *
 * @since 3.6
 */
@Named
@Singleton
public class DryRunPrefix
{
  private final String prefix;

  @Inject
  public DryRunPrefix(@Named("${nexus.log.dryrun.prefix:-::DRY RUN:: }") final String prefix) {
    this.prefix = checkNotNull(prefix);
  }

  public String get() {
    return prefix;
  }
}
