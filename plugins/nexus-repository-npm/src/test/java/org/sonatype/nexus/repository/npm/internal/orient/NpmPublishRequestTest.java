package org.sonatype.nexus.repository.npm.internal.orient;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.npm.internal.NpmPublishRequest;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import com.google.common.collect.ImmutableMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class NpmPublishRequestTest
    extends TestSupport
{
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  NestedAttributesMap packageRoot;

  @Mock
  TempBlob tempBlobA;

  @Mock
  TempBlob tempBlobB;

  @Test
  public void manageAndDeleteTempBlobsCorrectly() {
    try (NpmPublishRequest request = newNpmPublishRequest()) {
      assertThat(request.getPackageRoot(), is(packageRoot));
      assertThat(request.requireBlob("a"), is(tempBlobA));
      assertThat(request.requireBlob("b"), is(tempBlobB));
    }
    verify(tempBlobA).close();
    verify(tempBlobB).close();
  }

  @Test
  public void throwExceptionOnMissingTempBlob() {
    exception.expectMessage("blob-z");
    exception.expect(IllegalStateException.class);
    try (NpmPublishRequest request = newNpmPublishRequest()) {
      request.requireBlob("blob-z");
    }
  }

  private NpmPublishRequest newNpmPublishRequest() {
    return new NpmPublishRequest(packageRoot, ImmutableMap.of("a", tempBlobA, "b", tempBlobB));
  }
}
