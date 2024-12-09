package org.sonatype.nexus.extender.modules;

import com.google.inject.AbstractModule;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;

/**
 * SecurityFilter support bindings.
 * 
 * @since 3.0
 */
public class SecurityFilterModule
    extends AbstractModule
{
  // handle some edge-cases for commonly used filter-based components which need a bit
  // more configuration so that sisu/guice can find the correct bindings inside of plugins

  @Override
  protected void configure() {
    requireBinding(WebSecurityManager.class);
    requireBinding(FilterChainResolver.class);
  }
}
