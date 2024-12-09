package org.sonatype.nexus.supportzip;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Priority;
import org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Type;

import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * UT for {@link SanitizedXmlSourceSupport}.
 *
 * @since 3.0
 */
public class SanitizedXmlSourceSupportTest
    extends TestSupport
{
  /**
   * Tests that a sanitizer correctly sanitizes basic content based on a provided XSLT.
   */
  @Test
  public void testSanitizeContent() throws Exception {

    String expected = Resources.toString(Resources.getResource(getClass(), "output.xml"), Charset.forName("UTF-8"));
    String stylesheet = Resources.toString(Resources.getResource(getClass(), "sanitize.xsl"), Charset.forName("UTF-8"));

    File file = new File(Resources.getResource(getClass(), "input.xml").toURI());
    SanitizedXmlSourceSupport support = new SanitizedXmlSourceSupport(Type.CONFIG,
        "some/path",
        file,
        Priority.DEFAULT,
        stylesheet);

    support.prepare();

    assertEquals(expected, CharStreams.toString(new InputStreamReader(support.getContent(), "UTF-8")));
    assertEquals(expected.length(), support.getSize());
  }
}
