package org.sonatype.nexus.internal.node;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests for {@link NodeIdEncoding}.
 */
public class NodeIdEncodingTest
    extends TestSupport
{
  private static final String SHA1 = "05F4743FA756584643FDF9D0577BE4FB079289C6";

  private static final String NODE_ID = "05F4743F-A7565846-43FDF9D0-577BE4FB-079289C6";

  private static final String FINGERPRINT = "05:F4:74:3F:A7:56:58:46:43:FD:F9:D0:57:7B:E4:FB:07:92:89:C6";

  @Test
  public void nodeIdForSha1() {
    String output = NodeIdEncoding.nodeIdForSha1(SHA1);
    assertThat(output, equalTo(NODE_ID));
  }

  @Test
  public void sha1ForNodeId() {
    String output = NodeIdEncoding.sha1ForNodeId(NODE_ID);
    assertThat(output, equalTo(SHA1));
  }

  @Test
  public void nodeIdForFingerprint() {
    String output = NodeIdEncoding.nodeIdForFingerprint(FINGERPRINT);
    assertThat(output, equalTo(NODE_ID));
  }
}
