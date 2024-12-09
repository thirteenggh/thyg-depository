package org.sonatype.nexus.coreui;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.sonatype.goodies.testsupport.inject.InjectedTestSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.security.WebSecurityModule;
import org.sonatype.nexus.security.anonymous.AnonymousManager;
import org.sonatype.nexus.validation.ValidationModule;

import com.google.inject.Binder;
import org.apache.shiro.authc.credential.PasswordService;
import org.eclipse.sisu.space.BeanScanning;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoleXOTest
  extends InjectedTestSupport
{
  @Inject
  Validator validator;

  @Rule
  public TemporaryFolder tmp = new TemporaryFolder();

  @Override
  public void configure(final Binder binder) {
    super.configure(binder);
    binder.install(new ValidationModule());
    binder.install(new WebSecurityModule(mock(ServletContext.class)));
    binder.bind(EventManager.class).toInstance(mock(EventManager.class));
    binder.bind(AnonymousManager.class).toInstance(mock(AnonymousManager.class));

    binder.bind(PasswordService.class).toInstance(mock(PasswordService.class));

    ApplicationDirectories directories = mock(ApplicationDirectories.class);
    when(directories.getWorkDirectory()).thenAnswer(i -> tmp.newFolder());
    binder.bind(ApplicationDirectories.class).toInstance(directories);
  }

  @Override
  public BeanScanning scanning() {
    return BeanScanning.INDEX;
  }

  @Test
  public void testValidation_success() {
    RoleXO roleXO = new RoleXO();
    roleXO.setId("test");
    roleXO.setName("test");
    roleXO.setRoles(Collections.singleton("test2"));

    Set<ConstraintViolation<Object>> errors = validator.validate(roleXO);

    assertThat(errors.size(), is(0));
  }

  @Test
  public void testValidation_failure_includesSelf() {
    RoleXO roleXO = new RoleXO();
    roleXO.setId("test");
    roleXO.setName("test");
    roleXO.setRoles(Collections.singleton("test"));

    Set<ConstraintViolation<Object>> errors = validator.validate(roleXO);

    assertThat(errors.size(), is(1));
    assertThat(errors.iterator().next().getMessage(),
        is("A role cannot contain itself directly or indirectly through other roles."));
  }
}
