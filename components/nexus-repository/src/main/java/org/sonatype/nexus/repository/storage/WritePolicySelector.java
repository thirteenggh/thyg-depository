package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.repository.config.WritePolicy;

/**
 * Write policy selector.
 *
 * @since 3.0
 */
public interface WritePolicySelector
{
  /**
   * Default instance of {@link WritePolicy} selector, that returns the configured {@link WritePolicy} unchanged.
   */
  WritePolicySelector DEFAULT = new WritePolicySelector()
  {
    @Override
    public WritePolicy select(final Asset asset, final WritePolicy configured) {
      return configured;
    }
  };

  /**
   * Returns the effective {@link WritePolicy} for given asset blob changes.
   */
  WritePolicy select(final Asset asset, final WritePolicy configured);
}
