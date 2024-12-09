package org.sonatype.nexus.capability.condition.internal;

import java.security.NoSuchAlgorithmException;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.capability.condition.ConditionSupport;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.crypto.CryptoHelper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A condition that is satisfied if the JVM supports high-strength keys for a specific cipher algorithm.
 *
 * @since 2.7
 */
public class CipherKeyHighStrengthCondition
    extends ConditionSupport
    implements Condition
{
  /**
   * The minimum bit-length that is considered "high-strength".
   */
  public static final int MIN_BITS = 4096;

  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("JVM supports high-strength '%s' cipher keys")
    String satisfied(String transformation);

    @DefaultMessage("JVM does NOT support high-strength '%s' cipher keys")
    String unsatisfied(String transformation);
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final CryptoHelper crypto;

  private final String transformation;

  public CipherKeyHighStrengthCondition(final EventManager eventManager,
                                        final CryptoHelper crypto,
                                        final String transformation)
  {
    super(eventManager, false);
    this.crypto = checkNotNull(crypto);
    this.transformation = checkNotNull(transformation);
  }

  @Override
  protected void doBind() {
    getEventManager().register(this);
    verify();
  }

  @Override
  protected void doRelease() {
    getEventManager().unregister(this);
  }

  @Override
  public String toString() {
    return explainSatisfied();
  }

  @Override
  public String explainSatisfied() {
    return messages.satisfied(transformation);
  }

  @Override
  public String explainUnsatisfied() {
    return messages.unsatisfied(transformation);
  }

  private void verify() {
    try {
      int max = crypto.getCipherMaxAllowedKeyLength(transformation);
      if (max >= MIN_BITS) {
        setSatisfied(true);
      }
      else {
        setSatisfied(false);
      }
    }
    catch (NoSuchAlgorithmException e) {
      log.trace("Verify failure", e);
      setSatisfied(false);
    }
  }
}
