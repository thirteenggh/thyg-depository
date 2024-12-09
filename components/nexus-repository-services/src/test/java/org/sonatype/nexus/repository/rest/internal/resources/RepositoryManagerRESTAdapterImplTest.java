package org.sonatype.nexus.repository.rest.internal.resources;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.security.RepositoryPermissionChecker;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class RepositoryManagerRESTAdapterImplTest
    extends TestSupport
{
  private static final String REPOSITORY_NAME = "repoName";

  private static final String REPOSITORY_NAME_2 = "repoNameTwo";

  private static final String REPOSITORY_NAME_3 = "repoNameThree";

  private static final String REPOSITORY_FORMAT = "repoFormat";

  private static final String REPOSITORY_FORMAT_2 = "repoFormatTwo";

  private static final String REPOSITORY_FORMAT_3 = "repoFormatThree";

  private static final boolean PERMIT_BROWSE = true;

  @Mock
  RepositoryManager repositoryManager;

  @Mock
  Repository repository;

  @Mock
  Repository repository2;

  @Mock
  Repository repository3;

  @Mock
  Format repositoryFormat;

  @Mock
  Format repositoryFormat2;

  @Mock
  Format repositoryFormat3;

  @Mock
  RepositoryPermissionChecker repositoryPermissionChecker;

  RepositoryManagerRESTAdapterImpl underTest;

  @Before
  public void setUp() throws Exception {
    when(repositoryManager.get(REPOSITORY_NAME)).thenReturn(repository);
    when(repositoryManager.browse()).thenReturn(asList(repository, repository2, repository3));

    when(repository.getFormat()).thenReturn(repositoryFormat);
    when(repository2.getFormat()).thenReturn(repositoryFormat2);
    when(repository3.getFormat()).thenReturn(repositoryFormat3);

    when(repository.getName()).thenReturn(REPOSITORY_NAME);
    when(repository2.getName()).thenReturn(REPOSITORY_NAME_2);
    when(repository3.getName()).thenReturn(REPOSITORY_NAME_3);

    when(repositoryFormat.getValue()).thenReturn(REPOSITORY_FORMAT);
    when(repositoryFormat2.getValue()).thenReturn(REPOSITORY_FORMAT_2);
    when(repositoryFormat3.getValue()).thenReturn(REPOSITORY_FORMAT_3);

    underTest = new RepositoryManagerRESTAdapterImpl(repositoryManager, repositoryPermissionChecker);
  }

  @Test
  public void getRepository_allPermissions() throws Exception {
    configurePermissions(repository, PERMIT_BROWSE);
    assertThat(underTest.getRepository(REPOSITORY_NAME), is(repository));
  }

  @Test
  public void getRepository_browseOnly() throws Exception {
    configurePermissions(repository, PERMIT_BROWSE);
    assertThat(underTest.getRepository(REPOSITORY_NAME), is(repository));
  }

  @Test
  public void getRepository_readOnlyReturnsForbidden() throws Exception {
    configurePermissions(repository, !PERMIT_BROWSE);

    try {
      underTest.getRepository(REPOSITORY_NAME);
      fail(); //should have thrown exception
    }
    catch (WebApplicationException e) {
      assertThat(e.getResponse().getStatus(), is(403));
    }
  }

  @Test
  public void getRepository_cannotReadOrBrowse() {
    configurePermissions(repository, !PERMIT_BROWSE);
    try {
      underTest.getRepository(REPOSITORY_NAME);
      fail(); //should have thrown exception
    }
    catch (WebApplicationException e) {
      assertThat(e.getResponse().getStatus(), is(403));
    }
  }

  private void configurePermissions(final Repository repository, final boolean permitBrowse) {
    when(repositoryPermissionChecker.userCanBrowseRepository(repository)).thenReturn(permitBrowse);
  }

  @Test(expected = NotFoundException.class)
  public void getRepository_notFound() {
    underTest.getRepository("notFound");
  }

  @Test
  public void getRepository_null() {
    try {
      underTest.getRepository(null);
      fail(); //should have thrown exception
    }
    catch (WebApplicationException e) {
      assertThat(e.getResponse().getStatus(), is(422));
    }
  }

  @Test
  public void getRepositories() {
    when(repositoryPermissionChecker.userCanBrowseRepositories(asList(repository, repository2, repository3)))
        .thenReturn(asList(repository, repository2));

    assertThat(underTest.getRepositories(), is(asList(repository, repository2)));
  }

  @Test
  public void findContainingGroupsShouldDelegateToRepositoryManager() {
    String repositoryName = "aRepository";
    List<String> repositoryNames = asList("group1", "group2");
    when(repositoryManager.findContainingGroups(repositoryName)).thenReturn(repositoryNames);

    List<String> containingGroups = underTest.findContainingGroups(repositoryName);

    assertThat(containingGroups, is(repositoryNames));
  }
}
