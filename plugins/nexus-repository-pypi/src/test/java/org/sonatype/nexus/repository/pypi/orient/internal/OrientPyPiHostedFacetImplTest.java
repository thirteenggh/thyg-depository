package org.sonatype.nexus.repository.pypi.orient.internal;

import java.util.Arrays;
import java.util.List;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.template.TemplateHelper;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.pypi.internal.PyPiLink;
import org.sonatype.nexus.repository.pypi.orient.internal.OrientPyPiHostedFacetImpl;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.transaction.UnitOfWork;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class OrientPyPiHostedFacetImplTest
    extends TestSupport
{
  @Mock
  private TemplateHelper templateHelper;

  @Mock
  private StorageTx tx;

  @Mock
  private Repository repository;

  @Mock
  private Component component1;

  @Mock
  private Component component2;

  @Mock
  private Component component3;

  private OrientPyPiHostedFacetImpl underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new OrientPyPiHostedFacetImpl(templateHelper);

    UnitOfWork.beginBatch(tx);
  }

  @After
  public void teardown() {
    UnitOfWork.end();
  }

  @Test
  public void shouldOrderLinks() throws Exception {
    when(repository.getUrl()).thenReturn("http://localhost:8081/repository/test");
    when(component1.name()).thenReturn("z");
    when(component2.name()).thenReturn("a");
    when(component3.name()).thenReturn("0");
    when(tx.browseComponents(any())).thenReturn(Arrays.asList(
        component1, component2, component3
    ));
    underTest.attach(repository);

    List<PyPiLink> links = underTest.findAllLinks();

    String[] result = links.stream().map(PyPiLink::getFile).distinct().toArray(String[]::new);
    assertThat(result[0], is("0"));
    assertThat(result[1], is("a"));
    assertThat(result[2], is("z"));
  }
}
