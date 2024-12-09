package org.sonatype.nexus.repository.golang.internal;

import org.sonatype.nexus.repository.golang.GolangFormat;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GolangFormatTest
{
  private GolangFormat underTest;

  @Test
  public void checkFormatNameIsCorrect() {
    underTest = new GolangFormat();

    assertThat(underTest.getValue(), is(equalTo("go")));
  }
}
