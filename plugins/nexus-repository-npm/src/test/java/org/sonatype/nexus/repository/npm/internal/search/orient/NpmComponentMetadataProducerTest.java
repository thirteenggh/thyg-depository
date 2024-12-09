package org.sonatype.nexus.repository.npm.internal.search.orient;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.storage.Component;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class NpmComponentMetadataProducerTest
    extends TestSupport
{
  @Mock
  private Component component;

  private NpmComponentMetadataProducer underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new NpmComponentMetadataProducer(emptySet());
  }

  @Test
  public void isReleaseWhenNoDashInVersion() throws Exception {
    when(component.version()).thenReturn("1.0.0");
    assertThat(underTest.isPrerelease(component, emptyList()), is(false));
  }

  @Test
  public void isPreleaseWhenComponentHasDash() throws Exception {
    when(component.version()).thenReturn("1.0.0-pre");
    assertThat(underTest.isPrerelease(component, emptyList()), is(true));
  }
}
