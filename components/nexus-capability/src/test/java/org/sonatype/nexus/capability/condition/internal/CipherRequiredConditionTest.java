package org.sonatype.nexus.capability.condition.internal;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.sonatype.goodies.testsupport.TestSupport;
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
 * Tests for {@link CipherRequiredCondition}.
 *
 * @since 2.7
 */
public class CipherRequiredConditionTest
    extends TestSupport
{
  public static final String FAKE_TRANSFORMATION = "fake-transformation";

  private CipherRequiredCondition condition;

  @Mock
  private CryptoHelper crypto;

  @Before
  public void setUp() throws Exception {
    EventManager eventManager = mock(EventManager.class);
    condition = new CipherRequiredCondition(eventManager, crypto, FAKE_TRANSFORMATION);
  }

  @Test
  public void unsatisfiedWhenTransformMissing() throws Exception {
    when(crypto.createCipher(FAKE_TRANSFORMATION)).thenThrow(new NoSuchAlgorithmException());
    condition.bind();
    assertThat(condition.isSatisfied(), is(false));
  }

  @Test
  public void satisfiedWhenTransformAvailable() throws Exception {
    when(crypto.createCipher(FAKE_TRANSFORMATION)).thenReturn(mock(Cipher.class));
    condition.bind();
    assertThat(condition.isSatisfied(), is(true));
  }
}
