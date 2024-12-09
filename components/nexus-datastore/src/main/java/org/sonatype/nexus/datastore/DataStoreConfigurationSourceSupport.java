package org.sonatype.nexus.datastore;

import java.util.regex.Pattern;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.validation.constraint.NamePatternConstants;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.compile;

/**
 * Base class to collect common functions from multiple {@link DataStoreConfigurationSource} implementations.
 *
 * @since 3.25
 */
public abstract class DataStoreConfigurationSourceSupport
    extends ComponentSupport
    implements DataStoreConfigurationSource
{
  protected static final Pattern VALID_NAME_PATTERN = compile(NamePatternConstants.REGEX);

  /**
   * Checks the given store name is valid using the standard name regex.
   */
  protected static void checkName(final String storeName) {
    checkArgument(VALID_NAME_PATTERN.matcher(storeName).matches(), "%s is not a valid data store name", storeName);
  }
}
