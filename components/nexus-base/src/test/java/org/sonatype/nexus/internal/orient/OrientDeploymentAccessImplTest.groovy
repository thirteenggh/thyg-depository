
package org.sonatype.nexus.internal.orient

import org.sonatype.nexus.common.node.NodeAccess
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import static org.mockito.Mockito.when

/**
 * Unit tests for {@link DeploymentAccessImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
class OrientDeploymentAccessImplTest
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory('test')

  @Mock
  NodeAccess nodeAccess

  private OrientDeploymentAccessImpl service

  @Before
  void setup() {
    when(nodeAccess.getId()).thenReturn('test-id')
    service = new OrientDeploymentAccessImpl(database.instanceProvider, nodeAccess)
    service.start()
  }

  @Test
  void 'confirm id is present and alias is null in initial state'() {
    assert service.getId() == 'test-id'
    assert service.getAlias() == null
  }

  @Test
  void 'start is idempotent'() {
    def before = service.getId()

    service.start()

    assert before == service.getId()
  }

  @Test
  void 'setAlias is successful'() {
    assert service.getAlias() == null

    service.setAlias('some-new-alias')

    assert service.getId() != null
    assert service.getAlias() == 'some-new-alias'

    // accept null values
    service.setAlias(null)

    assert service.getAlias() == null

  }
}
