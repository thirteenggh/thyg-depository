package org.sonatype.nexus.capability.condition.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.capability.condition.internal.CipherKeyHighStrengthCondition;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.crypto.CryptoHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link CipherKeyHighStrengthCondition}.
 *
 * @since 2.7
 */
public class CipherKeyHighStrengthConditionTest
    extends TestSupport
{
  public static final String FAKE_TRANSFORMATION = "fake-transformation";

  private CipherKeyHighStrengthCondition condition;

  @Mock
  private CryptoHelper crypto;

  @Before
  public void setUp() throws Exception {
    EventManager eventManager = mock(EventManager.class);
    condition = new CipherKeyHighStrengthCondition(eventManager, crypto, FAKE_TRANSFORMATION);
  }

  @Test
  public void unsatisfiedWhenTransformStrengthIsLow() throws Exception {
    when(crypto.getCipherMaxAllowedKeyLength(FAKE_TRANSFORMATION))
        .thenReturn(CipherKeyHighStrengthCondition.MIN_BITS - 1);
    condition.bind();
    assertThat(condition.isSatisfied(), is(false));
  }

  @Test
  public void satisfiedWhenTransformStrengthIsHigh() throws Exception {
    when(crypto.getCipherMaxAllowedKeyLength(FAKE_TRANSFORMATION)).thenReturn(CipherKeyHighStrengthCondition.MIN_BITS);
    condition.bind();
    assertThat(condition.isSatisfied(), is(true));
  }

  @Test
  public void satisfiedWhenTransformStrengthIsHigher() throws Exception {
    when(crypto.getCipherMaxAllowedKeyLength(FAKE_TRANSFORMATION))
        .thenReturn(CipherKeyHighStrengthCondition.MIN_BITS + 1);
    condition.bind();
    assertThat(condition.isSatisfied(), is(true));
  }
}
