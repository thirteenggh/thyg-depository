package org.sonatype.nexus.repository.view.handlers;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.node.NodeAccess;

import org.fest.util.Strings;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import org.mockito.Mock;

public class HighAvailabilitySupportCheckerTest
    extends TestSupport
{
  private static final String FORMAT_NAME = "dummyFormat";

  private static final String HA_SUPPORTED_PROPERTY = String.format("nexus.%s.ha.supported", FORMAT_NAME);

  private static String haSupportedPropertyInitValue;

  private HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  @Mock
  private NodeAccess nodeAccess;

  @BeforeClass
  public static void init() {
    haSupportedPropertyInitValue = System.getProperty(HA_SUPPORTED_PROPERTY);
  }

  @AfterClass
  public static void tearDown() {
    if (!Strings.isNullOrEmpty(haSupportedPropertyInitValue)) {
      System.setProperty(HA_SUPPORTED_PROPERTY, haSupportedPropertyInitValue);
    }
    else {
      System.clearProperty(HA_SUPPORTED_PROPERTY);
    }
  }

  @Test
  public void returnTrue_IfNexusHAIsFalseAndFormatHAIsFalse() {
    when(nodeAccess.isClustered()).thenReturn(false);
    highAvailabilitySupportChecker = new HighAvailabilitySupportChecker(nodeAccess);
    System.setProperty(HA_SUPPORTED_PROPERTY, "false");
    assertThat(highAvailabilitySupportChecker.isSupported(FORMAT_NAME), is(true));
  }

  @Test
  public void returnFalse_IfNexusHAIsTrueAndFormatHAIsFalse() {
    when(nodeAccess.isClustered()).thenReturn(true);
    highAvailabilitySupportChecker = new HighAvailabilitySupportChecker(nodeAccess);
    System.setProperty(HA_SUPPORTED_PROPERTY, "false");
    assertThat(highAvailabilitySupportChecker.isSupported(FORMAT_NAME), is(false));
  }

  @Test
  public void returnTrue_IfNexusHAIsTrueAndFormatHAIsTrue() {
    when(nodeAccess.isClustered()).thenReturn(true);
    highAvailabilitySupportChecker = new HighAvailabilitySupportChecker(nodeAccess);
    System.setProperty(HA_SUPPORTED_PROPERTY, "true");
    assertThat(highAvailabilitySupportChecker.isSupported(FORMAT_NAME), is(true));
  }

  @Test
  public void returnTrue_IfNexusHAIsFalseAndFormatHAIsTrue() {
    when(nodeAccess.isClustered()).thenReturn(false);
    highAvailabilitySupportChecker = new HighAvailabilitySupportChecker(nodeAccess);
    System.setProperty(HA_SUPPORTED_PROPERTY, "true");
    assertThat(highAvailabilitySupportChecker.isSupported(FORMAT_NAME), is(true));
  }
}
