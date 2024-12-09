package org.sonatype.nexus.onboarding.internal;

import javax.validation.ConstraintViolationException;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.onboarding.OnboardingManager;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.config.AdminPasswordFileManager;
import org.sonatype.nexus.validation.ValidationModule;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.sonatype.nexus.onboarding.internal.OnboardingResource.PASSWORD_REQUIRED;

public class OnboardingResourceTest
    extends TestSupport
{
  @Mock
  private OnboardingManager onboardingManager;

  @Mock
  private SecuritySystem securitySystem;

  @Mock
  private ApplicationDirectories applicationDirectories;

  @Mock
  private AdminPasswordFileManager adminPasswordFileManager;

  private OnboardingResource underTest;

  @Before
  public void setup() {
    underTest = Guice.createInjector(new ValidationModule(), new AbstractModule()
    {
      @Override
      protected void configure() {
        bind(OnboardingManager.class).toInstance(onboardingManager);
        bind(SecuritySystem.class).toInstance(securitySystem);
        bind(ApplicationDirectories.class).toInstance(applicationDirectories);
        bind(AdminPasswordFileManager.class).toInstance(adminPasswordFileManager);
      }
    }).getInstance(OnboardingResource.class);
  }

  @Test
  public void testChangeAdminPassword() throws Exception {
    underTest.changeAdminPassword("newpass");

    verify(securitySystem).changePassword("admin", "newpass", false);
  }

  @Test
  public void testChangeAdminPassword_empty() {
    try {
      underTest.changeAdminPassword("");
      fail("empty password should have failed validation");
    }
    catch (ConstraintViolationException e) {
      assertThat(e.getConstraintViolations().iterator().next().getMessage(), is(PASSWORD_REQUIRED));
    }
  }

  @Test
  public void testChangeAdminPassword_null() {
    try {
      underTest.changeAdminPassword(null);
      fail("null password should have failed validation");
    }
    catch (ConstraintViolationException e) {
      assertThat(e.getConstraintViolations().iterator().next().getMessage(), is(PASSWORD_REQUIRED));
    }
  }
}
