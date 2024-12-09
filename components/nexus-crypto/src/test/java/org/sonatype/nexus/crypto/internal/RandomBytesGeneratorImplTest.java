package org.sonatype.nexus.crypto.internal;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link RandomBytesGeneratorImpl}.
 */
public class RandomBytesGeneratorImplTest
    extends TestSupport
{
  private RandomBytesGeneratorImpl generator;

  @Before
  public void setUp() throws Exception {
    this.generator = new RandomBytesGeneratorImpl(new CryptoHelperImpl());
  }

  @Test(expected = IllegalArgumentException.class)
  public void sizeZero() {
    generator.generate(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sizeNegative() {
    generator.generate(-1);
  }
}
