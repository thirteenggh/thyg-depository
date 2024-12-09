package org.sonatype.nexus.siesta.internal.resteasy;

import org.jboss.resteasy.spi.ResteasyDeployment;

/**
 * Sisu {@link ResteasyDeployment}.
 *
 * @since 3.0
 */
public class SisuResteasyDeployment
    extends ResteasyDeployment
{
  public SisuResteasyDeployment() {
    providerFactory = new SisuResteasyProviderFactory();
  }
}
