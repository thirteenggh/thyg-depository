package org.sonatype.nexus.internal.wonderland;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.crypto.internal.CryptoHelperImpl;
import org.sonatype.nexus.crypto.internal.RandomBytesGeneratorImpl;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link AuthTicketGenerator}.
 */
public class AuthTicketGeneratorTest
    extends TestSupport
{
  @Test
  public void generateWithDefault() {
    AuthTicketGenerator generator = new AuthTicketGenerator(new RandomBytesGeneratorImpl(new CryptoHelperImpl()), 16);
    String token = generator.generate();
    log(token);
    assertNotNull(token);
  }

  private void generateWithSize(final int size) {
    AuthTicketGenerator generator = new AuthTicketGenerator(new RandomBytesGeneratorImpl(new CryptoHelperImpl()), 16);
    String token = generator.generate(size);
    log(token);
    assertNotNull(token);
  }

  @Test
  public void generateWithSize32() {
    generateWithSize(32);
  }

  @Test
  public void generateWithSize66() {
    generateWithSize(66);
  }
}
