package org.sonatype.nexus.repository.p2.internal.util;

import java.io.IOException;
import java.io.InputStream;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class TempBlobConverterTest
    extends TestSupport
{
  private TempBlobConverter underTest;
  private static final String REAL_PACK_GZ = "com.google.code.atinject.tck_1.1.0.v20160901-1938.jar.pack.gz";

  @Mock
  private TempBlob tempBlob;

  @Mock
  private InputStream inputStream;

  @Before
  public void setUp() throws Exception {
    underTest = new TempBlobConverter();
  }

  @Test
  public void getInputStreamFromPackGzTempBlob() throws Exception {
    InputStream is = getClass().getResourceAsStream(REAL_PACK_GZ);
    when(tempBlob.get()).thenReturn(is);
    InputStream result = underTest.getJarFromPackGz(tempBlob);
    assertThat(result, is(instanceOf(InputStream.class)));
  }

  @Test(expected = IOException.class)
  public void getIOExceptionFromTempBlob() throws Exception {
    when(tempBlob.get()).thenReturn(inputStream);
    when(inputStream.read()).thenThrow(new IOException());
    underTest.getJarFromPackGz(tempBlob);
  }
}
