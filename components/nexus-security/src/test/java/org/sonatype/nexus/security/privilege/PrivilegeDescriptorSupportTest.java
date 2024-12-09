package org.sonatype.nexus.security.privilege;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.sonatype.nexus.security.privilege.PrivilegeDescriptorSupport.ALL;
import static org.sonatype.nexus.security.privilege.PrivilegeDescriptorSupport.humanizeActions;
import static org.sonatype.nexus.security.privilege.PrivilegeDescriptorSupport.humanizeName;

/**
 * Tests for {@link PrivilegeDescriptorSupport}.
 */
public class PrivilegeDescriptorSupportTest
    extends TestSupport
{
  @Test
  public void humanizeNameTest() throws Exception {
    assertThat(humanizeName(ALL, ALL), equalTo("all"));
    assertThat(humanizeName(ALL, "bar"), equalTo("all 'bar'-format"));
    assertThat(humanizeName("foo", "bar"), equalTo("foo"));
  }

  @Test
  public void humanizeActionsTest() throws Exception {
    assertThat(humanizeActions(ALL), equalTo("All privileges"));
    assertThat(humanizeActions("FOO"), equalTo("Foo privilege"));
    assertThat(humanizeActions("foo"), equalTo("Foo privilege"));
    assertThat(humanizeActions("FOO", "BAR", "BAZ"), equalTo("Foo, Bar, Baz privileges"));
    assertThat(humanizeActions("foo", "bar", "baz"), equalTo("Foo, Bar, Baz privileges"));
  }
}
