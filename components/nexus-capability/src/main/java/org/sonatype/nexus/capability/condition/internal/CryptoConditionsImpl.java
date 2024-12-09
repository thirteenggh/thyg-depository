package org.sonatype.nexus.capability.condition.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.capability.condition.CryptoConditions;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.crypto.CryptoHelper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of {@link CryptoConditions}.
 *
 * @since 2.7
 */
@Named
@Singleton
public class CryptoConditionsImpl
    implements CryptoConditions
{
  private final EventManager eventManager;

  private final CryptoHelper crypto;

  @Inject
  public CryptoConditionsImpl(final EventManager eventManager,
                          final CryptoHelper crypto)
  {
    this.eventManager = checkNotNull(eventManager);
    this.crypto = checkNotNull(crypto);
  }

  @Override
  public Condition requireCipher(final String algorithm) {
    return new CipherRequiredCondition(eventManager, crypto, algorithm);
  }

  @Override
  public Condition highStrengthCipherKey(final String algorithm) {
    return new CipherKeyHighStrengthCondition(eventManager, crypto, algorithm);
  }

  @Override
  public Condition unlimitedStrengthCipherKey(final String algorithm) {
    return new CipherKeyUnlimitedStrengthCondition(eventManager, crypto, algorithm);
  }
}
