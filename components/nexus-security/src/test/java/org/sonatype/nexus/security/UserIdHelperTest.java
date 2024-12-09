package org.sonatype.nexus.security;

import org.sonatype.goodies.testsupport.TestSupport;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.security.UserIdHelper.UNKNOWN;

/**
 * Tests for {@link UserIdHelper}.
 */
public class UserIdHelperTest
  extends TestSupport
{
  private Subject subject(final Object principal) {
    Subject subject = mock(Subject.class);
    when(subject.getPrincipal()).thenReturn(principal);
    return subject;
  }

  @Test
  public void get_subject() {
    assertThat(UserIdHelper.get(subject("test")), is("test"));
  }

  @Test
  public void get_nullSubject() {
    assertThat(UserIdHelper.get(null), is(UNKNOWN));
  }

  @Test
  public void get_nullPrincipal() {
    assertThat(UserIdHelper.get(subject(null)), is(UNKNOWN));
  }
}
