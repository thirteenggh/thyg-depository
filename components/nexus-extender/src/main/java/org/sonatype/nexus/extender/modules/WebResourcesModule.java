package org.sonatype.nexus.extender.modules;

import org.sonatype.nexus.webresources.WebResourceBundle;

import com.google.inject.AbstractModule;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * Provides common {@link WebResourceBundle} bindings.
 * 
 * @since 3.0
 */
public class WebResourcesModule
    extends AbstractModule
{
  private static final Named STATIC = Names.named("static");

  @Override
  protected void configure() {
    bind(WebResourceBundle.class).annotatedWith(STATIC).to(StaticWebResourceBundle.class).asEagerSingleton();
  }
}
