package org.sonatype.nexus.repository.httpbridge.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.capability.CapabilityContext;
import org.sonatype.nexus.capability.CapabilityDescriptor;
import org.sonatype.nexus.capability.CapabilityEvent;
import org.sonatype.nexus.capability.CapabilityReference;
import org.sonatype.nexus.capability.CapabilityType;
import org.sonatype.nexus.repository.httpbridge.legacy.LegacyUrlCapabilityDescriptor;
import org.sonatype.nexus.repository.httpbridge.legacy.LegacyUrlEnabledHelper;

import org.eclipse.sisu.inject.BindingPublisher;
import org.eclipse.sisu.inject.MutableBeanLocator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LegacyHttpBridgeServiceTest
    extends TestSupport
{
  @Mock
  private MutableBeanLocator locator;

  @Mock
  private LegacyUrlEnabledHelper legacyUrlEnabledHelper;

  @Mock
  private CapabilityEvent capabilityEvent;

  @Mock
  private CapabilityReference capabilityReference;

  @Mock
  private CapabilityContext capabilityContext;

  @Mock
  private CapabilityDescriptor capabilityDescriptor;

  @Mock
  private CapabilityType capabilityType;

  private LegacyHttpBridgeService underTest;

  @Before
  public void setup() throws Exception {
    when(legacyUrlEnabledHelper.isEnabled()).thenReturn(true);

    underTest = new LegacyHttpBridgeService(locator, legacyUrlEnabledHelper);

    when(capabilityEvent.getReference()).thenReturn(capabilityReference);
    when(capabilityReference.context()).thenReturn(capabilityContext);
    when(capabilityContext.descriptor()).thenReturn(new LegacyUrlCapabilityDescriptor());
  }

  @Test
  public void addModuleOnCreationWhenEnabled() throws Exception {
    verify(locator).add(any());
  }

  @Test
  public void doNotAddModuleOnCreationWhenDisabled() throws Exception {
    reset(locator);
    when(legacyUrlEnabledHelper.isEnabled()).thenReturn(false);

    underTest = new LegacyHttpBridgeService(locator, legacyUrlEnabledHelper);

    verify(locator, never()).add(any());
  }

  @Test
  public void addModuleOnCapabilityEventWhenMatches() throws Exception {
    underTest.handle(capabilityEvent);

    verify(locator).add(any());
  }

  @Test
  public void doNotAddModuleOnCapabilityEventWhenWrongEvent() throws Exception {
    reset(locator);

    when(capabilityDescriptor.type()).thenReturn(capabilityType);
    when(capabilityContext.descriptor()).thenReturn(capabilityDescriptor);

    underTest.handle(capabilityEvent);

    verify(locator, never()).add(any());
  }

  @Test
  public void removeModuleOnCapabilityEventWhenNotActive() throws Exception {
    when(legacyUrlEnabledHelper.isEnabled()).thenReturn(false);

    underTest.handle(capabilityEvent);

    verify(locator).remove(any(BindingPublisher.class));
  }

  @Test
  public void addOnceAndOnlyOnce() throws Exception {
    underTest.handle(capabilityEvent);
    underTest.handle(capabilityEvent);

    verify(locator).add(any());
  }
}
