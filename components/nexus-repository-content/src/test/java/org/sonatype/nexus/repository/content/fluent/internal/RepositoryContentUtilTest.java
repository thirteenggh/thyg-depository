package org.sonatype.nexus.repository.content.fluent.internal;

import java.util.Set;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.HostedType;

import org.junit.Test;
import org.mockito.Mock;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.repository.content.fluent.internal.RepositoryContentUtil.getLeafRepositoryIds;
import static org.sonatype.nexus.repository.content.fluent.internal.RepositoryContentUtil.isGroupRepository;

public class RepositoryContentUtilTest
    extends TestSupport
{
  @Mock
  private Repository repository;

  @Mock
  private Repository leafMember1;

  @Mock
  private Repository leafMember2;

  @Mock
  private GroupFacet groupFacet;

  @Mock
  private ContentFacet contentFacet;

  @Test
  public void shouldReturnRepositoryIds() {
    when(repository.facet(GroupFacet.class)).thenReturn(groupFacet);
    when(groupFacet.leafMembers()).thenReturn(asList(leafMember1, leafMember2));
    when(leafMember1.facet(ContentFacet.class)).thenReturn(contentFacet);
    when(leafMember2.facet(ContentFacet.class)).thenReturn(contentFacet);
    when(contentFacet.contentRepositoryId()).thenReturn(1, 2);

    Set<Integer> repositoryIds = getLeafRepositoryIds(repository);

    assertThat(repositoryIds, is(newHashSet(1, 2)));
  }

  @Test
  public void testBeTrueWhenGroupRepository() {
    when(repository.getType()).thenReturn(new GroupType());

    assertTrue(isGroupRepository(repository));
  }

  @Test
  public void testBeFalseWhenNotGroupRepository() {
    when(repository.getType()).thenReturn(new HostedType());

    assertFalse(isGroupRepository(repository));
  }
}
