package org.sonatype.nexus.blobstore.restore.orient;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.restore.BaseRestoreMetadataTaskDescriptor;

/**
 * @since 3.4
 */
@Named
@Singleton
public class OrientRestoreMetadataTaskDescriptor
    extends BaseRestoreMetadataTaskDescriptor
{
  public OrientRestoreMetadataTaskDescriptor() {
    super(OrientRestoreMetadataTask.class);
  }
}
