package org.sonatype.nexus.pax.exam.internal;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

import org.ops4j.pax.exam.ProbeInvokerFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator
    implements BundleActivator
{
  public void start(final BundleContext context) throws Exception {
    final Dictionary<String, ?> properties = new Hashtable<>(Collections.singletonMap("driver", "nexus"));
    context.registerService(ProbeInvokerFactory.class, new DelayedProbeInvokerFactory(context), properties);
  }

  public void stop(final BundleContext context) throws Exception {
    // nothing to do...
  }
}
