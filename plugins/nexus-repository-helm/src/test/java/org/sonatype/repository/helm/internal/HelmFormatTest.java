package org.sonatype.repository.helm.internal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelmFormatTest
{
  private HelmFormat underTest;

  @Test
  public void checkFormatNameIsCorrect() {
    underTest = new HelmFormat();

    assertThat(underTest.getValue(), is(equalTo("helm")));
  }
}
