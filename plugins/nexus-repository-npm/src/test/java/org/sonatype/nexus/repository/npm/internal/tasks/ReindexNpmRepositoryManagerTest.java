package org.sonatype.nexus.repository.npm.internal.tasks;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.ImmutableNestedAttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.attributes.AttributesFacet;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.npm.internal.tasks.orient.OrientUnprocessedRepositoryChecker;
import org.sonatype.nexus.scheduling.TaskConfiguration;
import org.sonatype.nexus.scheduling.TaskScheduler;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.repository.npm.internal.tasks.ReindexNpmRepositoryTaskDescriptor.REPOSITORY_NAME_FIELD_ID;
import static org.sonatype.nexus.repository.npm.internal.tasks.ReindexNpmRepositoryTaskDescriptor.TYPE_ID;
import static org.sonatype.nexus.repository.npm.internal.tasks.orient.OrientReindexNpmRepositoryTask.NPM_V1_SEARCH_UNSUPPORTED;

public class ReindexNpmRepositoryManagerTest
    extends TestSupport
{
  static final String REPOSITORY_NAME = "test-repository";

  @Mock
  TaskScheduler taskScheduler;

  @Mock
  RepositoryManager repositoryManager;

  @Mock
  Repository repository;

  @Mock
  AttributesFacet attributesFacet;

  @Mock
  ImmutableNestedAttributesMap repositoryAttributes;

  TaskConfiguration submittedTaskConfiguration = new TaskConfiguration();

  ReindexNpmRepositoryManager underTest;

  @Before
  public void setUp() {
    when(taskScheduler.createTaskConfigurationInstance(TYPE_ID)).thenReturn(submittedTaskConfiguration);
    when(taskScheduler.findAndSubmit(TYPE_ID, ImmutableMap.of(REPOSITORY_NAME_FIELD_ID, REPOSITORY_NAME)))
        .thenReturn(false);
    when(repositoryManager.browse()).thenReturn(singletonList(repository));
    when(repository.getName()).thenReturn(REPOSITORY_NAME);
    when(repository.facet(AttributesFacet.class)).thenReturn(attributesFacet);
    when(attributesFacet.getAttributes()).thenReturn(repositoryAttributes);
    when(repositoryAttributes.get(NPM_V1_SEARCH_UNSUPPORTED)).thenReturn(true);

    underTest = new ReindexNpmRepositoryManager(taskScheduler, repositoryManager, new OrientUnprocessedRepositoryChecker(), true);
  }

  @Test
  public void exceptionDoesNotPreventStartup() {
    when(repositoryManager.browse()).thenThrow(new RuntimeException("exception"));

    try {
      underTest.doStart();
    }
    catch (Exception e) {
      fail("expected startup to catch exceptions");
    }
  }

  @Test
  public void skipProcessingWhenNotEnabled() {
    underTest = new ReindexNpmRepositoryManager(taskScheduler, repositoryManager, new OrientUnprocessedRepositoryChecker(), false);

    underTest.doStart();

    verifyNoMoreInteractions(repositoryManager);
    verifyNoMoreInteractions(taskScheduler);
  }

  @Test
  public void skipRepositoryWithoutFlag() {
    when(repositoryAttributes.get(NPM_V1_SEARCH_UNSUPPORTED)).thenReturn(null);

    underTest.doStart();

    verify(taskScheduler, never()).submit(any(TaskConfiguration.class));
  }

  @Test
  public void skipRepositoryWithFalseFlag() {
    when(repositoryAttributes.get(NPM_V1_SEARCH_UNSUPPORTED)).thenReturn(false);

    underTest.doStart();

    verify(taskScheduler, never()).submit(any(TaskConfiguration.class));
  }

  @Test
  public void skipRepositoryWithRunningTask() {
    when(taskScheduler.findAndSubmit(TYPE_ID, ImmutableMap.of(REPOSITORY_NAME_FIELD_ID, REPOSITORY_NAME)))
        .thenReturn(true);

    underTest.doStart();

    verify(taskScheduler, never()).submit(any(TaskConfiguration.class));
  }

  @Test
  public void processRepositoryWithoutRunningTaskBasedOnTypeId() {
    underTest.doStart();

    verifySubmittedTaskConfiguration();
    verify(taskScheduler).submit(submittedTaskConfiguration);
  }

  @Test
  public void processRepositoryWithoutRunningTaskBasedOnRepositoryName() {
    underTest.doStart();

    verifySubmittedTaskConfiguration();
    verify(taskScheduler).submit(submittedTaskConfiguration);
  }

  @Test
  public void processRepositoryWithoutRunningTaskBasedOnCurrentState() {
    underTest.doStart();

    verifySubmittedTaskConfiguration();
    verify(taskScheduler).submit(submittedTaskConfiguration);
  }

  private void verifySubmittedTaskConfiguration() {
    assertThat(submittedTaskConfiguration.getString(REPOSITORY_NAME_FIELD_ID), is(REPOSITORY_NAME));
    assertThat(submittedTaskConfiguration.getName(), is("Reindex npm repository - (test-repository)"));
  }
}
