package org.sonatype.nexus.extender;

import java.util.Map;

import javax.servlet.ServletContext;

import org.sonatype.nexus.common.app.ApplicationVersion;
import org.sonatype.nexus.common.app.ManagedLifecycleManager;
import org.sonatype.nexus.common.guice.TimeTypeConverter;
import org.sonatype.nexus.common.stateguard.StateGuardModule;
import org.sonatype.nexus.security.WebSecurityModule;
import org.sonatype.nexus.transaction.TransactionModule;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.sisu.bean.BeanManager;
import org.eclipse.sisu.inject.DefaultBeanLocator;
import org.eclipse.sisu.inject.MutableBeanLocator;
import org.eclipse.sisu.osgi.ServiceBindings;
import org.eclipse.sisu.wire.ParameterKeys;
import org.osgi.framework.BundleContext;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.eclipse.sisu.osgi.ServiceBindings.defaultAllow;
import static org.eclipse.sisu.osgi.ServiceBindings.defaultIgnore;

/**
 * Binds the initial {@link ServletContext} and configures shiro.
 *
 * @since 3.0
 */
public class NexusContextModule
    extends AbstractModule
{
  private static final String ALLOW_SERVICES = "org.apache.karaf.*,org.sonatype.nexus.*," + defaultAllow();

  private static final String IGNORE_SERVICES = "org.apache.karaf.shell.commands.*," + defaultIgnore();

  private final BundleContext bundleContext;

  private final ServletContext servletContext;

  private final Map<?, ?> nexusProperties;

  public NexusContextModule(final BundleContext bundleContext,
                            final ServletContext servletContext,
                            final Map<?, ?> nexusProperties)
  {
    this.bundleContext = checkNotNull(bundleContext);
    this.servletContext = checkNotNull(servletContext);
    this.nexusProperties = checkNotNull(nexusProperties);
  }

  @Override
  protected void configure() {

    // we will look these up later...
    requireBinding(GuiceFilter.class);
    requireBinding(BeanManager.class);
    requireBinding(ApplicationVersion.class);

    bind(ServletContext.class).toInstance(servletContext);
    bind(ParameterKeys.PROPERTIES).toInstance(nexusProperties);

    install(new StateGuardModule());
    install(new TransactionModule());
    install(new TimeTypeConverter());
    install(new WebSecurityModule(servletContext));

    // enable OSGi service lookup of Karaf components
    final MutableBeanLocator locator = new DefaultBeanLocator();
    locator.add(new ServiceBindings(bundleContext, ALLOW_SERVICES, IGNORE_SERVICES, Integer.MIN_VALUE));
    bind(MutableBeanLocator.class).toInstance(locator);

    bind(ManagedLifecycleManager.class).toInstance(new NexusLifecycleManager(locator, bundleContext.getBundle(0)));
  }
}
