package org.sonatype.nexus.common.stateguard;

import org.sonatype.nexus.common.guice.AbstractInterceptorModule;

import com.google.inject.matcher.Matchers;

/**
 * State guard module.
 *
 * @since 3.0
 */
public class StateGuardModule
  extends AbstractInterceptorModule
{
  @Override
  protected void configure() {
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(Guarded.class), new GuardedInterceptor());
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transitions.class), new TransitionsInterceptor());
  }
}
