package org.sonatype.repository.conan.internal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConanFormatTest
{
  private ConanFormat underTest;

  @Test
  public void checkFormatNameIsCorrect() {
    underTest = new ConanFormat();

    assertThat(underTest.getValue(), is(equalTo("conan")));
  }
}
