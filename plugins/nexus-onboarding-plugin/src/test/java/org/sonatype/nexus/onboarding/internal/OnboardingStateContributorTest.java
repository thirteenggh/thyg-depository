package org.sonatype.nexus.onboarding.internal;

import java.util.Arrays;
import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.onboarding.OnboardingConfiguration;
import org.sonatype.nexus.onboarding.OnboardingItem;
import org.sonatype.nexus.onboarding.OnboardingManager;
import org.sonatype.nexus.security.config.AdminPasswordFileManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;

public class OnboardingStateContributorTest
    extends TestSupport
{
  @Mock
  private OnboardingConfiguration onboardingConfiguration;

  @Mock
  private OnboardingManager onboardingManager;

  @Mock
  private AdminPasswordFileManager adminPasswordFileManager;

  @Mock
  private OnboardingItem onboardingItem1;

  @Mock
  private OnboardingItem onboardingItem2;

  private OnboardingStateContributor underTest;

  @Before
  public void setup() {
    when(onboardingConfiguration.isEnabled()).thenReturn(true);
    when(onboardingManager.getOnboardingItems()).thenReturn(Arrays.asList(onboardingItem1, onboardingItem2));
    when(onboardingManager.needsOnboarding()).thenReturn(true);
    when(adminPasswordFileManager.exists()).thenReturn(true);
    when(adminPasswordFileManager.getPath()).thenReturn("path/to/file");

    underTest = new OnboardingStateContributor(onboardingConfiguration, onboardingManager, adminPasswordFileManager);
  }

  @Test
  public void testGetState() {
    Map<String, Object> state = underTest.getState();
    assertThat(state.size(), is(2));
    assertThat(state.get("onboarding.required"), is(true));
    assertThat(state.get("admin.password.file"), is("path/to/file"));
  }

  @Test
  public void testGetState_noItems() {
    when(onboardingManager.needsOnboarding()).thenReturn(false);
    when(adminPasswordFileManager.exists()).thenReturn(false);

    assertThat(underTest.getState(), nullValue());
  }

  @Test
  public void testGetState_cacheOnboardingState() {
    Map<String, Object> state = underTest.getState();
    assertThat(state.get("onboarding.required"), is(true));

    when(onboardingManager.needsOnboarding()).thenReturn(false);

    state = underTest.getState();
    assertThat(state.get("onboarding.required"), nullValue());

    //set to true to validate that cache kicks in and still doesn't add data to the map
    when(onboardingManager.needsOnboarding()).thenReturn(true);

    state = underTest.getState();
    assertThat(state.get("onboarding.required"), nullValue());
  }

  @Test
  public void testGetState_noAdminPasswordFile() {
    when(adminPasswordFileManager.exists()).thenReturn(false);

    Map<String, Object> state = underTest.getState();
    assertThat(state.get("admin.password.file"), nullValue());
  }
}
