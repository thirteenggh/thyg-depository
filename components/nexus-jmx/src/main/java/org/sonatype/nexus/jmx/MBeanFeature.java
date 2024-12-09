package org.sonatype.nexus.jmx;

import javax.management.MBeanFeatureInfo;

/**
 * MBean feature.
 *
 * @since 3.0
 */
public interface MBeanFeature
{
  /**
   * Feature information.
   */
  MBeanFeatureInfo getInfo();
}
