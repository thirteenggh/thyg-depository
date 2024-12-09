package org.sonatype.nexus.audit.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.capability.CapabilityReferenceFilterBuilder.CapabilityReferenceFilter;
import org.sonatype.nexus.capability.CapabilityRegistry;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuditCapabilityBooterTest
    extends TestSupport
{
  private AuditCapabilityBooter underTest;

  @Mock
  private CapabilityRegistry capabilityRegistry;

  @Before
  public void setup() {
    underTest = new AuditCapabilityBooter();
  }

  @Test
  public void testBoot_isEnabled() throws Exception {
    when(capabilityRegistry.get(any(CapabilityReferenceFilter.class))).thenReturn(null);
    underTest.boot(capabilityRegistry);

    verify(capabilityRegistry).add(eq(AuditCapability.TYPE), eq(true), any(), any());
  }
}
