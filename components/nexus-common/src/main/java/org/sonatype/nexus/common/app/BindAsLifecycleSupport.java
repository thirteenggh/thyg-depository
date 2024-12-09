package org.sonatype.nexus.common.app;

import javax.inject.Provider;

import org.sonatype.goodies.lifecycle.Lifecycle;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.eclipse.sisu.inject.TypeArguments;

import static com.google.inject.name.Names.named;

/**
 * Utility class to help bind {@link Provider} components as managed {@link Lifecycle}s.
 *
 * Provider implementations are not automatically exposed under additional interfaces.
 * This small module is a workaround to expose this provider as a (managed) lifecycle.
 *
 * @since 3.16
 */
public class BindAsLifecycleSupport<T extends Lifecycle & Provider<?>>
    extends AbstractModule
{
  @Override
  @SuppressWarnings("unchecked")
  protected void configure() {
    // make sure we pick up the right (super) type argument, i.e. Foo from BindAsLifecycleSupport<Foo>
    TypeLiteral<?> superType = TypeLiteral.get(getClass()).getSupertype(BindAsLifecycleSupport.class);
    TypeLiteral<T> typeArgument = (TypeLiteral<T>) TypeArguments.get(superType, 0);

    // bind using a unique key to avoid clashing with any other 'BindAsLifecycle' in the same bundle
    bind(Lifecycle.class).annotatedWith(named(typeArgument.getRawType().getName())).to(typeArgument);
  }
}
