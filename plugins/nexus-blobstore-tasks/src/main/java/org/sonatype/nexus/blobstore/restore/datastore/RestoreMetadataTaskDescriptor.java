package org.sonatype.nexus.blobstore.restore.datastore;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.restore.BaseRestoreMetadataTaskDescriptor;

/**
 * @since 3.4
 */
@Named
@Singleton
public class RestoreMetadataTaskDescriptor
    extends BaseRestoreMetadataTaskDescriptor
{
 public RestoreMetadataTaskDescriptor() {
   super(RestoreMetadataTask.class);
 }
}
