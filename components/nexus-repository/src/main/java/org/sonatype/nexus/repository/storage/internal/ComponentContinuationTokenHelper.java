package org.sonatype.nexus.repository.storage.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.orient.entity.OrientContinuationTokenHelper;
import org.sonatype.nexus.repository.storage.ComponentEntityAdapter;

/**
 * {@link OrientContinuationTokenHelper} for the {@link ComponentEntityAdapter}
 *
 * @since 3.7
 */
@Named("component")
@Singleton
public class ComponentContinuationTokenHelper
    extends OrientContinuationTokenHelper
{
  @Inject
  public ComponentContinuationTokenHelper(ComponentEntityAdapter entityAdapter) {
    super(entityAdapter);
  }
}
