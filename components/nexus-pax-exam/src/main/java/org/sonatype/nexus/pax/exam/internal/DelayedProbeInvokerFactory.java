package org.sonatype.nexus.pax.exam.internal;

import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.equinox.region.Region;
import org.eclipse.equinox.region.RegionDigraph;
import org.ops4j.pax.exam.ProbeInvoker;
import org.ops4j.pax.exam.ProbeInvokerFactory;
import org.ops4j.pax.exam.TestContainerException;
import org.ops4j.pax.exam.util.Injector;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.hooks.bundle.EventHook;
import org.osgi.framework.hooks.service.EventListenerHook;
import org.osgi.framework.hooks.service.FindHook;
import org.osgi.util.tracker.ServiceTracker;

import static java.util.Collections.singletonMap;
import static org.osgi.framework.Constants.SERVICE_RANKING;
import static org.sonatype.nexus.pax.exam.NexusPaxExamSupport.NEXUS_PAX_EXAM_INVOKER_DEFAULT;
import static org.sonatype.nexus.pax.exam.NexusPaxExamSupport.NEXUS_PAX_EXAM_INVOKER_KEY;
import static org.sonatype.nexus.pax.exam.NexusPaxExamSupport.NEXUS_PAX_EXAM_TIMEOUT_DEFAULT;
import static org.sonatype.nexus.pax.exam.NexusPaxExamSupport.NEXUS_PAX_EXAM_TIMEOUT_KEY;

public class DelayedProbeInvokerFactory
    implements ProbeInvokerFactory
{
  private static final Logger log = Logger.getLogger(DelayedProbeInvokerFactory.class.getName());

  private final long examTimeout;

  private final String examInvoker;

  private final RegionDigraph regionDigraph;

  private final ServiceTracker<?, ProbeInvokerFactory> probeFactoryTracker;

  private final ServiceTracker<?, Injector> nexusInjectorTracker;

  public DelayedProbeInvokerFactory(final BundleContext context) {
    examTimeout = getExamTimeout(context);
    examInvoker = getExamInvoker(context);

    regionDigraph = context.getService(context.getServiceReference(RegionDigraph.class));

    probeFactoryTracker = new ServiceTracker<>(context, probeInvokerFilter(), null);
    nexusInjectorTracker = new ServiceTracker<>(context, nexusInjectorFilter(), null);

    probeFactoryTracker.open();
    nexusInjectorTracker.open();

    // workaround https://issues.apache.org/jira/browse/KARAF-4899
    installRegionWorkaround(context);
  }

  @Override
  public ProbeInvoker createProbeInvoker(final Object context, final String expr) {
    return new DelegatingProbeInvoker(() -> doCreateProbeInvoker(context, expr));
  }

  private ProbeInvoker doCreateProbeInvoker(final Object context, final String expr) {
    try {
      if (nexusInjectorTracker.waitForService(examTimeout) != null) {

        // use the real Pax-Exam invoker factory to supply the testsuite invoker
        return probeFactoryTracker.getService().createProbeInvoker(context, expr);
      }
      throw new TestContainerException("Trust Repository failed to start after " + examTimeout + "ms");
    }
    catch (final InterruptedException e) {
      throw new TestContainerException("Trust Repository failed to start after " + examTimeout + "ms", e);
    }
  }

  private static int getExamTimeout(final BundleContext context) {
    final String value = context.getProperty(NEXUS_PAX_EXAM_TIMEOUT_KEY);
    if (value == null || value.trim().length() == 0) {
      return NEXUS_PAX_EXAM_TIMEOUT_DEFAULT;
    }
    try {
      return Integer.parseInt(value);
    }
    catch (final NumberFormatException e) {
      return NEXUS_PAX_EXAM_TIMEOUT_DEFAULT;
    }
  }

  /**
   * @return Name of the real Pax-Exam {@link ProbeInvokerFactory}
   */
  private static String getExamInvoker(final BundleContext context) {
    final String value = context.getProperty(NEXUS_PAX_EXAM_INVOKER_KEY);
    if (value == null || value.trim().length() == 0) {
      return NEXUS_PAX_EXAM_INVOKER_DEFAULT;
    }
    return value;
  }

  /**
   * @return LDAP filter that matches the real Pax-Exam {@link ProbeInvokerFactory}
   */
  private Filter probeInvokerFilter() {
    return filter("(&(objectClass=%s)(driver=%s))", ProbeInvokerFactory.class.getName(), examInvoker);
  }

  /**
   * @return LDAP filter that matches the nexus-specific Pax-Exam {@link Injector}
   */
  private Filter nexusInjectorFilter() {
    return filter("(&(objectClass=%s)(name=nexus))", Injector.class.getName());
  }

  /**
   * @return LDAP filter created by formatting the given arguments
   */
  private static Filter filter(final String format, final Object... args) {
    try {
      return FrameworkUtil.createFilter(String.format(format, args));
    }
    catch (final InvalidSyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Workaround https://issues.apache.org/jira/browse/KARAF-4899 by
   * re-adding probe bundle to root region before any service lookup
   * (avoids spurious service lookup issues due to region filtering)
   */
  private void installRegionWorkaround(final BundleContext context) {
    Map<String, ?> maxRanking = singletonMap(SERVICE_RANKING, Integer.MAX_VALUE);

    // add probe to root region on install
    context.registerService(EventHook.class,
        (event, contexts) -> {
          if (event.getType() == BundleEvent.INSTALLED) {
            fixProbeRegion(event.getBundle());
          }
        }, new Hashtable<>(maxRanking));

    // add probe to root region whenever a service it's listening to changes
    context.registerService(EventListenerHook.class,
        (event, listeners) -> {
          if (((String[]) event.getServiceReference().getProperty(Constants.OBJECTCLASS))[0].contains("pax.exam")) {
            listeners.keySet().stream().map(BundleContext::getBundle).forEach(this::fixProbeRegion);
          }
        }, new Hashtable<>(maxRanking));

    // add probe to root region whenever it's directly looking up a service
    context.registerService(FindHook.class,
        (findContext, name, filter, allServices, references) -> {
          if ((name != null && name.contains("pax.exam")) || (filter != null && filter.contains("pax.exam"))) {
            fixProbeRegion(findContext.getBundle());
          }
        }, new Hashtable<>(maxRanking));
  }

  /**
   * If given bundle is the probe then make sure it's associated with the current root region.
   */
  private void fixProbeRegion(final Bundle bundle) {
    if (bundle.getSymbolicName().startsWith("PAXEXAM-PROBE")) {
      Region rootRegion = regionDigraph.getRegion("root");
      try {
        if (!rootRegion.contains(bundle)) {
          log.info("Adding bundle " + bundle + " to root region");
          rootRegion.addBundle(bundle);
        }
      }
      catch (final BundleException e) {
        log.log(Level.WARNING, "Unable to add bundle " + bundle + " to root region", e);
      }
    }
  }
}
