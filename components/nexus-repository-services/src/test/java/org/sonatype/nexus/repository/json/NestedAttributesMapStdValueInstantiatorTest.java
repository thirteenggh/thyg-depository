package org.sonatype.nexus.repository.json;

import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.NestedAttributesMap;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.google.common.collect.Maps.newHashMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class NestedAttributesMapStdValueInstantiatorTest
    extends TestSupport
{
  @Mock
  private StdValueInstantiator stdValueInstantiator;

  @Mock
  private NestedAttributesMap nestedAttributesMap;

  @Mock
  private DeserializationContext context;

  private Map<String, Object> map = newHashMap();

  private NestedAttributesMapStdValueInstantiator underTest;

  @Before
  public void setUp() {
    underTest = new NestedAttributesMapStdValueInstantiator(nestedAttributesMap);
    when(nestedAttributesMap.backing()).thenReturn(map);
  }

  @Test
  public void assure_CreateUsingDefault_Returns_backing() {
    assertThat(underTest.createUsingDefault(context), equalTo(map));
  }
}
