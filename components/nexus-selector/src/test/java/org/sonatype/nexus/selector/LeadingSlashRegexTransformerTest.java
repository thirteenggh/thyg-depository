package org.sonatype.nexus.selector;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.sonatype.nexus.selector.LeadingSlashRegexTransformer.trimLeadingSlashes;

public class LeadingSlashRegexTransformerTest
    extends TestSupport
{
  @Test
  public void expectedLeadingSlashTransformations() {
    assertThat(trimLeadingSlashes(""), is(""));
    assertThat(trimLeadingSlashes("/"), is(""));
    assertThat(trimLeadingSlashes(".*"), is(".*"));
    assertThat(trimLeadingSlashes("/.*"), is(".*"));
    assertThat(trimLeadingSlashes(".*/"), is(".*(^|/)"));
    assertThat(trimLeadingSlashes("/.*/"), is(".*/"));
    assertThat(trimLeadingSlashes(".?/foo/"), is(".?(^|/)foo/"));
    assertThat(trimLeadingSlashes("/+foo/"), is("(^|/)+foo/"));
    assertThat(trimLeadingSlashes("/com|/org"), is("com|org"));
    assertThat(trimLeadingSlashes("(?!.*/struts/.*).*/apache/.*"), is("(?!.*(^|/)struts/.*).*(^|/)apache/.*"));
    assertThat(trimLeadingSlashes("((([a-z[A-Z]]/)?([0-9]?/)*)|.*/?)/foo"), is("((([a-z[A-Z]]/)?([0-9]?(^|/))*)|.*/?)foo"));
  }
}
