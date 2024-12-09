package org.sonatype.nexus.repository.security.internal;

import java.util.HashMap;
import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class VariableResolverAdapterManagerImplTest
    extends TestSupport
{
  @Mock
  private VariableResolverAdapter specializedAdapter;

  @Mock
  private VariableResolverAdapter defaultAdapter;

  private VariableResolverAdapterManagerImpl manager;

  @Before
  public void setUp() {
    Map<String, VariableResolverAdapter> adaptersByFormat = new HashMap<>();
    adaptersByFormat.put("special", specializedAdapter);
    adaptersByFormat.put(VariableResolverAdapterManagerImpl.DEFAULT_ADAPTER_NAME, defaultAdapter);
    manager = new VariableResolverAdapterManagerImpl(adaptersByFormat);
  }

  @Test
  public void testGet_SpecializedAdapter() {
    assertThat(manager.get("special"), is(specializedAdapter));
  }

  @Test
  public void testGet_DefaultAdapter() {
    assertThat(manager.get("other-format"), is(defaultAdapter));
  }
}
