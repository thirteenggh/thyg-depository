package org.sonatype.nexus.repository.security;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.view.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SecurityHandlerTest
    extends TestSupport
{
  private SecurityHandler underTest;

  @Mock
  private Context context;

  @Mock
  private Repository repository;

  @Mock
  private SecurityFacet securityFacet;

  private AttributesMap attributesMap;

  @Before
  public void setup() {
    underTest = new SecurityHandler();

    attributesMap = new AttributesMap();
    when(repository.facet(SecurityFacet.class)).thenReturn(securityFacet);
    when(context.getRepository()).thenReturn(repository);
    when(context.getAttributes()).thenReturn(attributesMap);
  }

  @Test
  public void testHandle() throws Exception {
    underTest.handle(context);
    verify(securityFacet).ensurePermitted(any());
  }

  @Test
  public void testHandle_alreadyAuthorized() throws Exception {
    attributesMap.set(SecurityHandler.AUTHORIZED_KEY, true);
    underTest.handle(context);
    verify(securityFacet, never()).ensurePermitted(any());
  }
}
