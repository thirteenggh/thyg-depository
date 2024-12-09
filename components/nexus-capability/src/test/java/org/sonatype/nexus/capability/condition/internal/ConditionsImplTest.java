package org.sonatype.nexus.capability.condition.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.capability.condition.CapabilityConditions;
import org.sonatype.nexus.capability.condition.CryptoConditions;
import org.sonatype.nexus.capability.condition.LogicalConditions;
import org.sonatype.nexus.capability.condition.NexusConditions;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * {@link ConditionsImpl} UTs.
 *
 * @since capabilities 2.0
 */
public class ConditionsImplTest
    extends TestSupport
{

  /**
   * Passed in factories are returned.
   */
  @Test
  public void and01() {
    final LogicalConditions logicalConditions = mock(LogicalConditions.class);
    final CapabilityConditions capabilityConditions = mock(CapabilityConditions.class);
    final NexusConditions nexusConditions = mock(NexusConditions.class);
    CryptoConditions cryptoConditions = mock(CryptoConditions.class);
    final ConditionsImpl underTest = new ConditionsImpl(
        logicalConditions, capabilityConditions, nexusConditions, cryptoConditions
    );
    assertThat(underTest.logical(), is(equalTo(logicalConditions)));
    assertThat(underTest.capabilities(), is(equalTo(capabilityConditions)));
    assertThat(underTest.nexus(), is(equalTo(nexusConditions)));
  }

}
