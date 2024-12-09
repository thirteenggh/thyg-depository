package org.sonatype.nexus.repository.search.query;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.search.query.SearchSubjectHelper.SubjectRegistration;

import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SearchSubjectHelperTest
    extends TestSupport
{
  @Mock
  Subject subject;

  SearchSubjectHelper helper;

  @Before
  public void setup() {
    helper = new SearchSubjectHelper();
  }

  @Test
  public void testRegistration() {
    assertThat(helper.subjects.size(), is(0));
    try (SubjectRegistration registration = helper.register(subject)) {
      assertThat(helper.subjects.size(), is(1));
      assertThat(helper.getSubject(registration.getId()), is(subject));
    }
    assertThat(helper.subjects.size(), is(0));
  }

  @Test(expected = NullPointerException.class)
  public void testMissingSubject() {
    helper.getSubject("");
  }
}
