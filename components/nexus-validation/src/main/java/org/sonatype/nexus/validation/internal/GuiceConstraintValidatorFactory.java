package org.sonatype.nexus.validation.internal;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import org.sonatype.goodies.common.ComponentSupport;

import com.google.inject.Key;
import org.eclipse.sisu.inject.BeanLocator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Allow {@link ConstraintValidator validators} to be enhanced by Guice.
 *
 * @since 3.0
 */
@Singleton
public final class GuiceConstraintValidatorFactory
    extends ComponentSupport
    implements ConstraintValidatorFactory
{
  private final BeanLocator beanLocator;

  @Inject
  public GuiceConstraintValidatorFactory(final BeanLocator beanLocator) {
    this.beanLocator = checkNotNull(beanLocator);
  }

  public <T extends ConstraintValidator<?, ?>> T getInstance(final Class<T> key) {
    log.trace("Resolving validator instance for type: {}", key);
    return beanLocator.locate(Key.get(key)).iterator().next().getValue();
  }

  @Override
  public void releaseInstance(final ConstraintValidator<?, ?> instance) {
    // empty
  }
}
