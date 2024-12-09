package org.sonatype.nexus.coreui

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.common.event.EventManager
import org.sonatype.nexus.extdirect.model.StoreLoadParameters
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.manager.RepositoryManager
import org.sonatype.nexus.repository.manager.internal.RepositoryImpl
import org.sonatype.nexus.repository.security.RepositoryPermissionChecker
import org.sonatype.nexus.repository.types.HostedType

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.hamcrest.Matchers.is
import static org.junit.Assert.assertThat
import static org.mockito.Mockito.verify
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when
import static org.mockito.Mockito.mock

/**
 * Test for {@link RepositoryComponent}
 */
class RepositoryComponentTest
    extends TestSupport
{
  @Mock
  Format format

  @Mock
  EventManager eventManager

  @Mock
  RepositoryManager repositoryManager

  @Mock
  RepositoryPermissionChecker repositoryPermissionChecker

  Repository repository

  RepositoryComponent underTest

  @Before
  void setup() {
    repository = repository()

    when(format.getValue()).thenReturn('format')
    when(repositoryManager.browse()).thenReturn([repository])

    underTest = new RepositoryComponent()
    underTest.repositoryManager = repositoryManager
    underTest.repositoryPermissionChecker = repositoryPermissionChecker
  }

  @Test
  void checkUserPermissionsOnFilter() {
    underTest.filter(new StoreLoadParameters(filter: []))
    verify(repositoryPermissionChecker).userCanBrowseRepositories([repository] as List<Repository>)
  }

  @Test
  void filterForAutocomplete() {
    List<Repository> repositories = getTestRepositories()
    StoreLoadParameters storeLoadParameters = new StoreLoadParameters(filter: [])
    storeLoadParameters.setQuery("nug");
    List<RepositoryReferenceXO> result = underTest.filterForAutocomplete(storeLoadParameters, repositories)
    assertThat(result, hasSize(2))
    assertThat(result.get(0).getName(), is("nuget-proxy"));
    assertThat(result.get(1).getName(), is("nuget-hosted"));
  }

  List<Repository> getTestRepositories() {
    Repository nugetRepoProxy = mock(Repository.class);
    when(nugetRepoProxy.getName()).thenReturn("nuget-proxy")
    Repository nugetRepoHosted = mock(Repository.class);
    when(nugetRepoHosted.getName()).thenReturn("nuget-hosted")
    Repository mavenRepoHosted = mock(Repository.class);
    when(mavenRepoHosted.getName()).thenReturn("maven-hosted")
    List<Repository> repositories = new ArrayList<>()
    repositories.add(nugetRepoProxy)
    repositories.add(nugetRepoHosted)
    repositories.add(mavenRepoHosted)
    return repositories
  }


  Repository repository() {
    def repository = new RepositoryImpl(eventManager, new HostedType(), format)
    repository.name = 'repository'
    repository
  }
}
