package org.sonatype.nexus.coreui.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.upload.UploadManager;
import org.sonatype.nexus.repository.upload.UploadResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class UploadServiceTest
    extends TestSupport
{
  private static final String REPO_NAME = "repo";

  private UploadService component;

  @Mock
  private UploadManager uploadManager;

  @Mock
  private RepositoryManager repositoryManager;

  @Mock
  private Repository repo;

  @Mock
  private HttpServletRequest request;

  @Before
  public void setup() throws IOException {
    when(repositoryManager.get(REPO_NAME)).thenReturn(repo);

    UploadResponse uploadResponse = new UploadResponse(Collections.singletonList("foo"));
    when(uploadManager.handle(repo, request)).thenReturn(uploadResponse);

    component = new UploadService(repositoryManager, uploadManager);
  }

  @Test
  public void testUpload_unknownRepository() throws IOException {
    try {
      component.upload("foo", request);
      fail("Expected exception to be thrown");
    }
    catch (NullPointerException e) {
      assertThat(e.getMessage(), is("Specified repository is missing"));
    }
  }

  @Test
  public void testUpload() throws IOException {
    assertThat(component.upload(REPO_NAME, request), is("foo"));
  }

  @Test
  public void testCreateSearchTerm() {
    String result = component
        .createSearchTerm(Arrays.asList("foo-x.z/bar/bar", "foo-x.z/bar/foo", "foo-x.z/bar/foo/bar"));

    assertThat(result, is("foo-x.z/bar"));
  }
}
