package org.sonatype.nexus.repository.maven.internal.orient;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.storage.Component;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Collections.emptySet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class Maven2ComponentMetadataProducerTest
    extends TestSupport
{
  private Maven2ComponentMetadataProducer underTest;

  @Mock
  private NestedAttributesMap attributes;

  @Mock
  private Component component;

  @Mock
  private NestedAttributesMap childAttributes;

  @Before
  public void setUp() throws Exception {
    underTest = new Maven2ComponentMetadataProducer(emptySet());
  }

  @Test
  public void isReleaseWhenBaseVersionNull() throws Exception {
    when(component.attributes()).thenReturn(attributes);
    when(attributes.child(Maven2Format.NAME)).thenReturn(childAttributes);
    when(childAttributes.get("baseVersion")).thenReturn(null);

    assertThat(underTest.isPrerelease(component, emptySet()), is(false));
  }

  @Test
  public void isReleaseWhenBaseVersionNotSnapshot() throws Exception {
    when(component.attributes()).thenReturn(attributes);
    when(attributes.child(Maven2Format.NAME)).thenReturn(childAttributes);
    when(childAttributes.get("baseVersion")).thenReturn("1.0.0");

    assertThat(underTest.isPrerelease(component, emptySet()), is(false));
  }

  @Test
  public void isPreReleaseWhenBaseVersionEndWithSnapshot() throws Exception {
    when(component.attributes()).thenReturn(attributes);
    when(attributes.child(Maven2Format.NAME)).thenReturn(childAttributes);
    when(childAttributes.get("baseVersion")).thenReturn("1.0.0-SNAPSHOT");

    assertThat(underTest.isPrerelease(component, emptySet()), is(true));
  }
}
