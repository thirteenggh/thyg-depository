package org.sonatype.nexus.blobstore.api;

/**
 * @since 3.29
 */
public interface ChangeRepositoryBlobstoreDataService
{
  int changeRepoTaskUsingBlobstoreCount(final String blobStoreName);
}
