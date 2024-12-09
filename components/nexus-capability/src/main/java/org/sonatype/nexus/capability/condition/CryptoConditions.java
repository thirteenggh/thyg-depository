package org.sonatype.nexus.capability.condition;

import org.sonatype.nexus.capability.Condition;

/**
 * Factory of {@link Condition}s related to crypto support.
 *
 * @since 2.7
 */
public interface CryptoConditions
{
  Condition requireCipher(String algorithm);

  Condition highStrengthCipherKey(String algorithm);

  Condition unlimitedStrengthCipherKey(String algorithm);
}
