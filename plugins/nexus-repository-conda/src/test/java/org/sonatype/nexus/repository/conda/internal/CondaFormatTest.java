package org.sonatype.nexus.repository.conda.internal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @since 3.19
 */
public class CondaFormatTest
{
  @Test
  public void formatNameIsCorrectTest() {
    CondaFormat underTest = new CondaFormat();

    assertThat(underTest.getValue(), is(equalTo(CondaFormat.NAME)));
  }
}
