package org.sonatype.nexus.repository.p2.internal;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class P2FormatTest
    extends TestSupport
{
  P2Format p2Format;

  @Before
  public void setUp() {
    p2Format = new P2Format();
  }

  @Test
  public void P2FormatName() {
    assertThat(P2Format.NAME, is(equalTo("p2")));
  }
}
