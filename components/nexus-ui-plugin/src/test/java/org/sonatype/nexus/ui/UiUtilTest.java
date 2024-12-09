package org.sonatype.nexus.ui;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

import org.sonatype.goodies.testsupport.TestSupport;

import org.eclipse.sisu.space.ClassSpace;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static java.util.Collections.enumeration;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class UiUtilTest
    extends TestSupport
{
  @Mock
  private ClassSpace space;

  @Test
  public void getHashedFilename() throws Exception {
    Enumeration<URL> mockedResponse = enumeration(asList(
        new File("/nexus-frontend-bundle.js").toURI().toURL()
    ));
    when(space.findEntries("static", "nexus-frontend-bundle.js", true)).thenReturn(mockedResponse);

    String hashedFilename = UiUtil.getPathForFile("nexus-frontend-bundle.js", space);

    assertThat(hashedFilename, is("/nexus-frontend-bundle.js"));
  }
}
