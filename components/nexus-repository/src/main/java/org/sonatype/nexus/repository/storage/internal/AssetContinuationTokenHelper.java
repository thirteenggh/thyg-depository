package org.sonatype.nexus.repository.storage.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.orient.entity.OrientContinuationTokenHelper;
import org.sonatype.nexus.repository.storage.AssetEntityAdapter;

/**
 * {@link OrientContinuationTokenHelper} for the {@link AssetEntityAdapter}
 *
 * @since 3.7
 */
@Named("asset")
@Singleton
public class AssetContinuationTokenHelper
    extends OrientContinuationTokenHelper
{
  @Inject
  public AssetContinuationTokenHelper(AssetEntityAdapter entityAdapter) {
    super(entityAdapter);
  }
}
