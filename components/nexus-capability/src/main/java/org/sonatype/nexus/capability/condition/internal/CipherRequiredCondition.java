package org.sonatype.nexus.capability.condition.internal;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.capability.condition.ConditionSupport;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.crypto.CryptoHelper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A condition that is satisfied if the JVM supports a specific cipher algorithm.
 *
 * @since 2.7
 */
public class CipherRequiredCondition
    extends ConditionSupport
    implements Condition
{
  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("JVM supports '%s' cipher")
    String satisfied(String transformation);

    @DefaultMessage("JVM does NOT support '%s' cipher")
    String unsatisfied(String transformation);
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final CryptoHelper crypto;

  private final String transformation;

  public CipherRequiredCondition(final EventManager eventManager,
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
      crypto.createCipher(transformation);
      setSatisfied(true);
    }
    catch (Exception e) {
      log.trace("Verify failure", e);
      setSatisfied(false);
    }
  }
}
