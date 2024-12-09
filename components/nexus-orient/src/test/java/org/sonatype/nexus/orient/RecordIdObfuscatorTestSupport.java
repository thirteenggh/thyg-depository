package org.sonatype.nexus.orient;

import org.sonatype.goodies.testsupport.TestSupport;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Support for {@link RecordIdObfuscator} tests.
 */
public abstract class RecordIdObfuscatorTestSupport
  extends TestSupport
{
  private RecordIdObfuscator underTest;

  @Before
  public void setUp() throws Exception {
    this.underTest = createTestSubject();
  }

  protected abstract RecordIdObfuscator createTestSubject() throws Exception;

  @Test
  public void encodeAndDecode() {
    ORID rid = new ORecordId("#9:1");
    OClass type = mock(OClass.class);
    when(type.getClusterIds()).thenReturn(new int[] { rid.getClusterId() });
    log("RID: {}", rid);
    String encoded = underTest.encode(type, rid);
    log("Encoded: {}", encoded);
    ORID decoded = underTest.decode(type, encoded);
    log("Decoded: {}", decoded);
    assertThat(decoded.toString(), is("#9:1"));
  }

  @Test
  public void verifyIdTypeMismatchFails() {
    ORID rid = new ORecordId("#9:1");
    OClass type = mock(OClass.class);
    when(type.getClusterIds()).thenReturn(new int[] { 1 });
    String encoded = underTest.encode(type, rid);

    // this should fail since the cluster-id of the RID is not a member of the given type
    try {
      underTest.decode(type, encoded);
      fail();
    }
    catch (IllegalArgumentException e) {
      // expected
    }
  }
}
